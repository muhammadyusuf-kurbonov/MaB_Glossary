package uz.qmgroup.mab_glossary.features.search.datasource.localDB

import uz.qmgroup.mab_glossary.features.search.models.Term

fun LocalDBTermEntity.toTerm(): Term {
    return Term(id, term, definition)
}