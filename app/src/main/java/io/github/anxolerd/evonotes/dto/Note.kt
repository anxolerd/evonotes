package io.github.anxolerd.evonotes.dto

import org.threeten.bp.LocalDateTime

public data class Note(var id: Long? = null, internal val dateCreated: LocalDateTime, internal var dateModified: LocalDateTime, var content: String)