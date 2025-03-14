# DEPRECATED

For the upcoming launch of the swiyu Public Beta Trust Infrastructure we created a new organisation [swiyu-admin-ch](https://github.com/swiyu-admin-ch). 

This repository will be archived in the near term. New releases are published under [https://github.com/swiyu-admin-ch/eidch-elfa-licencecheck](https://github.com/swiyu-admin-ch/eidch-elfa-licencecheck).

# LicenceCheck

<img src="./elfa-vz-verifier-ui/src/assets/images/favicon.png" width="196"  alt="LicenceCheck logo"/>

An official Swiss Government project made by the Federal Office of Information Technology, Systems and Telecommunication
FOITT as part of the electronic identity (E-ID) project.

## Table of contents

  * [Overview](#overview)
  * [Project Setup](#project-setup)
    * [Prerequisites](#prerequisites)
    * [Technologies Used](#technologies-used)
  * [Usage Instructions](#usage-instructions)
    * [Building the application](#building-the-application)
    * [Using the frontend Angular application](#using-the-frontend-angular-application)
  * [Other Repositories](#other-repositories)
  * [Contribution Guide](#contribution-guide)
  * [License](#license)

## Overview

LicenceCheck is part of the pilot project developed for the future official Swiss E-ID.

It is publicly accessible and can be used to start a verification process to verify credentials.
The depth of information can be chosen through use-cases in LicenceCheck.
After approving/rejecting the scanned QR-Code, the result is displayed in LicenceCheck.

The public application can be found here: https://licencecheck.astra.admin.ch/

## Project Setup

### Prerequisites

| Technology | Required Version |
|------------|------------------|
| Java       | >= 17            |
| node       | >= 20            |
| npm        | >= 10            |
| mvn        | >= 3.9.3         |

### Technologies Used

| Component              | Technology/Framework  |
|------------------------|-----------------------|
| Backend                | Spring Boot 3         |
| Frontend               | Angular               |
| Outgoing REST Requests | Retrofit              |
| QR Code Generation     | zxing                 |
| Image Generation       | ImageIO               |

## Usage Instructions

> [!IMPORTANT]
> Due to security reasons, all secrets and files containing secrets have been removed from the project. It is still
> possible to test it locally with mocked data.

### Building the application

To run the application, you need to:

1. Go into `./elfa-vz-verifier-ui/` folder and run `npm install`
2. Run `mvn clean install` in root folder
4. Run `java -jar -Dspring.profiles.active=local ./elfa-vz-verifier-scs/target/elfa-vz-verifier-scs.jar`

When the server has finished starting, it is possible to access the following addresses:

| Application  | Link                                  |
|:-------------|:--------------------------------------|
| LicenceCheck | http://localhost:8090/                |
| SwaggerUI    | http://localhost:8090/swagger-ui.html |

### Using the frontend Angular application

The backend has mock implementations to enable local usage.
If a new verification process is started, there will always be successful verification because the underlying infrastructure has
been mocked. To change the mock response,
the [MockTechAdapterClientImpl.java](elfa-vz-verifier-scs%2Fsrc%2Fmain%2Fjava%2Fch%2Fadmin%2Fastra%2Felfa%2Ftrotti%2Fdomain%2Ftechadapter%2Fclient%2Fimpl%2FMockTechAdapterClientImpl.java)
can be modified at line 25 or 26 to get different responses.

## Other Repositories

* Android App: [pilot-android-wallet](https://github.com/e-id-admin/eidch-pilot-android-wallet)
* iOS App: [pilot-ios-wallet](https://github.com/e-id-admin/eidch-pilot-ios-wallet)
* Issuer, Verifier & Base
  registry: [pilot-elfa-base-infrastructure](https://github.com/e-id-admin/eidch-pilot-elfa-base-infrastructure)

## Contribution Guide

We welcome any feedback on the code regarding both the implementation and security aspects. Please follow the guidelines
for contributing found in [CONTRIBUTING.md](./CONTRIBUTING.md).

## License

This project is licensed under the terms of the MIT license. See the [LICENSE](LICENSE) file for details.
