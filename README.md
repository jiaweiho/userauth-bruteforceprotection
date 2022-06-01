# User Auth Backend

This was done as an assignment for SEB. The constraints and requirements was described in a separate pdf file.

## Overview

### WebSecurityConfig

The configuration uses Spring security where MyUserDetailsService will load the user by username.
If user is not authenticated then AuthenticationFailureListener and AuthenticationSuccessListener
will be called. This way there was no need to create a specific endpoint that
would've authenticated. Now endpoints are specified and applied through the above mentioned class's configure
method configure().

### BruteForceProtectionService

Implemented by DefaultBruteForceProtectionService which uses username to track. It's configurable
and specified under application.properties.
For extension would be to add IP tracking for safer protection.

### Flyway migration

Added this for easier migration of table changes.

### User and User login in database

User keeps track of username and password only whereas user login will keep track of
all failed attempts. E.g. the number of failed attempts, if maximum attempts has been reached and
the failed attempts should be done within a window of time. The user will be blocked

### Integration Test

There is integration test that shows it's able to authenticate the user and
when it's unauthenticated.

## Final Remarks of this assignment

As mentioned integration tests exist, but when run through as a batch it doesn't pass.
Only when run individually with exception of ...thenReset.

I haven't been able to verify the solution with the Angular client because of an error on
CORS. I tried to add the origin and configurations around it but couldn't get it to work.
I've tried to do with my best effort, within the scope and timeline and my capability.  