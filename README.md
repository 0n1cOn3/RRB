# RailRoad-Backend

A java Spring application backend for Railroad System.

The project powers a railroad operations platform, exposing APIs for schedules, routes, and reporting so other services (or a UI) can manage and observe network data. It focuses on reliable data access and operational insights, serving as the backend foundation for the broader Railroad System.

## Prober instructions and usage guideline

A simple HTTP prober relies only on standard CLI tools. Make sure you have `curl` available:

- **macOS**: `brew install curl`
- **Linux (Debian/Ubuntu)**: `sudo apt-get update && sudo apt-get install -y curl`
- **Windows (via WSL or Git Bash)**: `sudo apt-get update && sudo apt-get install -y curl`

A lightweight HTTP prober can be used as a smoke test to confirm the backend is running and connected to the database. Follow these steps:

1. Start the application locally:

   ```bash
   ./mvnw spring-boot:run
   ```

2. From another terminal, call a read-only endpoint that does not require authentication (for example, listing routes):

   ```bash
   curl -f "http://localhost:8080/report/routes?international=false"
   ```

   A `200 OK` response with a JSON payload indicates the service is healthy. The `-f` flag makes `curl` return a non-zero exit code if the endpoint is unreachable or returns an error status, which is suitable for automated probes.

### Usage guidelines

- **Target URL**: Adjust the host and port if the service is deployed elsewhere (e.g., a Kubernetes ClusterIP or an ingress address).
- **Timeouts**: Add `--max-time 5` (or another small value) to fail fast in automation pipelines.
- **Frequency**: Run the probe after deployments or before executing integration suites to ensure the backend is ready.
- **Non-destructive checks**: Prefer read-only endpoints such as `/report/routes` to avoid mutating data during health checks.
