package uz.qmgroup.mab_glossary.features.synchronize.remote

import uz.qmgroup.mab_glossary.features.search.models.Term

fun FirebaseTermEntity.toDomain(): Term = Term(id, term, definition)
fun Term.toRemoteEntity(): FirebaseTermEntity = FirebaseTermEntity(
    id ?: throw IllegalArgumentException("Convert only saved terms to remote"),
    term,
    definition
)