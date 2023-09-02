# RSocket-jwt-auth-using-asymmetric-key
here, I try to implement security RSocket application using jwt asymmetric private and public key. In this implementation, client needs to send token, to get response from RSocket server. To complete this. I use NimbusReactiveJwtDecoder for PublicKey builder and also customize ReactiveJwtAuthenticationConverterAdapter to verify user request with database data.

## Architecture Flow
<img width="287" alt="image" src="https://github.com/kckrepository/RSocket-jwt-auth/assets/17265754/872628a6-8e8a-4cbd-85ec-d711720b015a">
