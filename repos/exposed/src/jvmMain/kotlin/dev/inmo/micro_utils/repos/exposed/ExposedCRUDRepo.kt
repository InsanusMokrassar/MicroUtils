package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.*

interface ExposedCRUDRepo<ObjectType, IdType> : CommonExposedRepo<IdType, ObjectType>
