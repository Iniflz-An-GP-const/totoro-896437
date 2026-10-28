package rubery.totoro.digital.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rubery.totoro.digital.data.database.converter.Converters
import rubery.totoro.digital.data.database.dao.WorkoutSessionDao
import rubery.totoro.digital.data.entity.WorkoutSessionEntity

@Database(
    entities = [WorkoutSessionEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class RROILAppDatabase : RoomDatabase() {

    abstract fun workoutSessionDao(): WorkoutSessionDao
}
