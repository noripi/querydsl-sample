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
import com.querydsl.core.support.QueryBase
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Predicate
import com.querydsl.sql.ProjectableSQLQuery
import com.querydsl.sql.SQLQueryFactory

infix fun SQLQueryFactory.SELECT(exprs: Array<Expression<*>>) = this.select(*exprs)
infix fun <T, Q> ProjectableSQLQuery<T, Q>.FROM(arg: Expression<*>) where Q : ProjectableSQLQuery<T, Q>, Q : Query<Q> = this.from(
        arg)

infix fun <T, Q> ProjectableSQLQuery<T, Q>.JOIN(target: EntityPath<*>) where Q : ProjectableSQLQuery<T, Q>, Q : Query<Q> = this.join(
        target)

infix fun <T, Q> ProjectableSQLQuery<T, Q>.ON(condition: Predicate) where Q : ProjectableSQLQuery<T, Q>, Q : Query<Q> = this.on(
        condition)

infix fun <Q : QueryBase<Q>> QueryBase<Q>.WHERE(o: Predicate) = this.where(o)
infix fun <Q : QueryBase<Q>> QueryBase<Q>.LIMIT(limit: Long) = this.limit(limit)