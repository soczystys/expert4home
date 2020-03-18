# Expert4Home

## Configuration

| Parameter         | Value |
|-------------------|:-------------:|
| JDK version       | 1.8 |
| Database address  | localhost |
| Database port     | ${EXPERT4HOME_DB_PORT} |
| Database name     | db1 |
| Database username | root |
| Database password | ${EXPERT4HOME_DB_PASSWORD} |

## Launching

### Front End

Run task `bootFrontEnd`.

Note! If you get `Something is already running on port ####` error,
try to run `killNodeProcess` task first.

### Full Stack

Run task `bootFullStack`.

Note! React's hot reload will not be available. If you need it,
launch front end separately.
