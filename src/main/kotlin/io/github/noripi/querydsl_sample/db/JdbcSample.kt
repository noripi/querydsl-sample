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

import io.github.noripi.querydsl_sample.obj.Restaurant
import java.sql.DriverManager

object JdbcSample {
    fun getRestaurant(id: Int): Restaurant? {
        val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", DB_USER, DB_PASS)

        val statement = conn.prepareStatement("""
            SELECT restaurant_id, restaurant_name
            FROM restaurant
            WHERE restaurant_id = ?
            LIMIT 1
        """)
        statement.setInt(1, id)

        val resultSet = statement.executeQuery()
        val restaurant = if (resultSet.next()) {
            Restaurant(
                    id = resultSet.getInt("restaurant_id"),
                    name = resultSet.getString("restaurant_name")
            )
        } else {
            null
        }

        resultSet.close()
        statement.close()
        conn.close()

        return restaurant
    }
}


