/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Noriyuki Ishida
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.noripi.querydsl_sample.extension

import com.querydsl.core.Query
import com.querydsl.core.QueryFlag
import com.querydsl.core.support.QueryBase
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Expression
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Path
import com.querydsl.core.types.Predicate
import com.querydsl.sql.AbstractSQLQueryFactory
import com.querydsl.sql.ProjectableSQLQuery
import com.querydsl.sql.RelationalPath
import com.querydsl.sql.SQLCommonQuery
import com.querydsl.sql.dml.SQLInsertClause

/**
 * QueryFactory extensions
 */
infix fun <Q: SQLCommonQuery<*>> AbstractSQLQueryFactory<Q>.SELECT(exprs: Array<Expression<*>>) = this.select(*exprs)
infix fun <Q: SQLCommonQuery<*>> AbstractSQLQueryFactory<Q>.INSERT_INTO(path: RelationalPath<*>) = this.insert(path)

/**
 * INSERT extensions
 */
infix fun SQLInsertClause.COLUMNS(columns: Array<Path<*>>) = this.columns(*columns)
infix fun SQLInsertClause.VALUES(values: Array<Any>) = this.values(*values)

fun SQLInsertClause.onDuplicateKeyUpdate(vararg expressions: Expression<*>): SQLInsertClause {
    val placeholder = (0 until expressions.size).joinToString(", ") { "{$it}" }

    return this.addFlag(QueryFlag.Position.END,
            ExpressionUtils.template(String::class.java, " on duplicate key update $placeholder",
                    *expressions))

}

infix fun SQLInsertClause.ON_DUPLICATE_KEY_UPDATE(expressions: Array<Expression<*>>) = this.onDuplicateKeyUpdate(*expressions)
infix fun SQLInsertClause.ON_DUPLICATE_KEY_UPDATE(expression: Expression<*>) = this.onDuplicateKeyUpdate(expression)

/**
 * SELECT extensions
 */
infix fun <T, Q> ProjectableSQLQuery<T, Q>.FROM(arg: Expression<*>) where Q : ProjectableSQLQuery<T, Q>, Q : Query<Q> = this.from(arg)
infix fun <T, Q> ProjectableSQLQuery<T, Q>.JOIN(target: EntityPath<*>) where Q : ProjectableSQLQuery<T, Q>, Q : Query<Q> = this.join(target)
infix fun <T, Q> ProjectableSQLQuery<T, Q>.ON(condition: Predicate) where Q : ProjectableSQLQuery<T, Q>, Q : Query<Q> = this.on(condition)
infix fun <Q : QueryBase<Q>> QueryBase<Q>.WHERE(o: Predicate) = this.where(o)
infix fun <Q : QueryBase<Q>> QueryBase<Q>.LIMIT(limit: Long) = this.limit(limit)