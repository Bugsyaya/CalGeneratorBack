package helper

import java.sql.ResultSet

object Utils {
	def results[T](resultSet: ResultSet)(f: ResultSet => T) = {
		new Iterator[T] {
			def hasNext = resultSet.next()
			def next() = f(resultSet)
		}
	}
}
