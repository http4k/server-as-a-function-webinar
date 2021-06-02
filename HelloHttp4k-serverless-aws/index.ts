import * as pulumi from "@pulumi/pulumi";
import * as aws from "@pulumi/aws";
import {RolePolicyAttachment} from "@pulumi/aws/iam";

const defaultRole = new aws.iam.Role("hello-http4k-aws-default-role", {
    assumeRolePolicy: `{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
`
});


new RolePolicyAttachment("hello-http4k-aws-default-role-policy",
    {
        role: defaultRole,
        policyArn: aws.iam.ManagedPolicies.AWSLambdaBasicExecutionRole
    });

const lambdaFunction = new aws.lambda.Function("hello-http4k-aws", {
    code: new pulumi.asset.FileArchive("build/distributions/HelloHttp4k.zip"),
    handler: "com.example.Pokemon4kLambda",
    role: defaultRole.arn,
    runtime: "java11"
});

const logGroupApi = new aws.cloudwatch.LogGroup("hello-http4k-aws-api-route", {
    name: "hello-http4k-aws",
});

const apiGatewayPermission = new aws.lambda.Permission("hello-http4k-aws-gateway-permission", {
    action: "lambda:InvokeFunction",
    "function": lambdaFunction.name,
    principal: "apigateway.amazonaws.com"
});

const api = new aws.apigatewayv2.Api("hello-http4k-aws-api", {
    protocolType: "HTTP"
});

const apiDefaultStage = new aws.apigatewayv2.Stage("default", {
    apiId: api.id,
    autoDeploy: true,
    name: "$default",
    accessLogSettings: {
        destinationArn: logGroupApi.arn,
        format: `{"requestId": "$context.requestId", "requestTime": "$context.requestTime", "httpMethod": "$context.httpMethod", "httpPath": "$context.path", "status": "$context.status", "integrationError": "$context.integrationErrorMessage"}`
    }
})

const lambdaIntegration = new aws.apigatewayv2.Integration("hello-http4k-aws-api-lambda-integration", {
    apiId: api.id,
    integrationType: "AWS_PROXY",
    integrationUri: lambdaFunction.arn,
    payloadFormatVersion: "1.0"
});

const apiDefaultRole = new aws.apigatewayv2.Route("hello-http4k-aws-api-route", {
    apiId: api.id,
    routeKey: `$default`,
    target: pulumi.interpolate `integrations/${lambdaIntegration.id}`
});

export const publishedUrl = apiDefaultStage.invokeUrl;
