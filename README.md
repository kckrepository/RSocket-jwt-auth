# RSocket-jwt-auth-using-asymmetric-key
here, I try to implement security RSocket application using jwt asymmetric private and public key.
in this implementation, client needs to send token, to get response from RSocket server.
to complete this i use NimbusReactiveJwtDecoder for PublicKey builder and also customize ReactiveJwtAuthenticationConverterAdapter 
to verify user request with database data.
