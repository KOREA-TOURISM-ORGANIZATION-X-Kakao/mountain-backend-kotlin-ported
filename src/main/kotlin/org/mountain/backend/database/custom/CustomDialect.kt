package org.mountain.backend.database.custom

import org.hibernate.dialect.MySQL55Dialect
import org.hibernate.dialect.function.SQLFunctionTemplate
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.DateType
import org.hibernate.type.StringType

class CustomDialect : MySQL55Dialect {

    constructor() {
        registerFunction("GROUP_CONCAT", StandardSQLFunction("group_concat", StringType.INSTANCE))
        registerFunction("date_sub", SQLFunctionTemplate(DateType.INSTANCE, "DATE_ADD(?1, INTERVAL - ?2 WEEK)"))
        registerFunction("date_sub_month", SQLFunctionTemplate(DateType.INSTANCE, "DATE_ADD(?1, INTERVAL - ?2 MONTH)"))
    }

}