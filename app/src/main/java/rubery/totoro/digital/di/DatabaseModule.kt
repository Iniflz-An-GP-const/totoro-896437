package rubery.totoro.digital.di

import androidx.room.Room
import rubery.totoro.digital.data.database.RROILAppDatabase
import org.koin.dsl.module

private const val DB_NAME = "rroil_sport_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = RROILAppDatabase::class.java,
            name = DB_NAME,
        ).build()
    }

    single { get<RROILAppDatabase>().workoutSessionDao() }
}
