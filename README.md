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

UUser keeps track of username and password only whereas user login will keep track of
all failed attempts.

## Final Remarks of this assignment

The solution has tests that shows the authentication works if user 