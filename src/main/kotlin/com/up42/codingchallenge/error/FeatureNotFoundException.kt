package com.up42.codingchallenge.error

import java.util.UUID

class FeatureNotFoundException(
    id: UUID
): Exception("No features found with ID: $id")