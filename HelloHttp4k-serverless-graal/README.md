# HelloHttp4k-serverless-graal

This module deploys the Pokémon app to AWS Lambda using the custom HTTP runtime and compiled to a native binary with GraalVM.

### To deploy the stack:

- Install and configure Pulumi with your AWS credentials
- Run `./deploy.sh`
- Browse to `http://<pulumi output url>/b`

### To destroy:

- Run `./destroy.sh`