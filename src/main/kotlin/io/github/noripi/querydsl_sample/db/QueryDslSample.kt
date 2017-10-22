/**
 * Copyright (C) 2017 Retty, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 *
 * @author Noriyuki Ishida
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
    fun getRestaurant(id: Int): Restaurant? {
        val conf = Configuration(MySQLTemplates())
        val factory = SQLQueryFactory(conf, MysqlDataSource().apply {
            this.setUrl("jdbc:mysql://localhost:3306/test")
            this.setUser(DB_USER)
            this.setPassword(DB_PASS)
        })

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