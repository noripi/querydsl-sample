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
package io.github.noripi.querydsl_sample.db

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource
import com.querydsl.core.Tuple
import com.querydsl.sql.Configuration
import com.querydsl.sql.MySQLTemplates
import com.querydsl.sql.SQLQueryFactory
import io.github.noripi.querydsl_sample.obj.Restaurant
import io.github.noripi.querydsl_sample.query.QRestaurant

private val r = QRestaurant("r")

object QueryDslSample {
    private val factory = SQLQueryFactory(Configuration(MySQLTemplates()),
            MysqlDataSource().apply {
                this.setUrl("jdbc:mysql://localhost:3306/test")
                this.setUser(DB_USER)
                this.setPassword(DB_PASS)
            })

    fun getRestaurant(id: Int): Restaurant? {
        val result: Tuple? = factory.select(r.restaurantId, r.restaurantName)
                .from(r)
                .where(r.restaurantId.eq(id))
                .limit(1)
                .fetchOne()

        return result?.toRestaurant()
    }
}

private fun Tuple.toRestaurant(): Restaurant = Restaurant(
        id = this.get(r.restaurantId)!!,
        name = this.get(r.restaurantName)!!
)