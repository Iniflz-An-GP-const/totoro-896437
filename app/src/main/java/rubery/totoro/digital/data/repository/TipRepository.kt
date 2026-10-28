package rubery.totoro.digital.data.repository

import rubery.totoro.digital.data.model.TipModel
import rubery.totoro.digital.data.model.TipText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TipRepository {
    private val tips = listOf(
        TipModel(
            id = 1,
            texts = mapOf(
                "en" to TipText(
                    title = "Stay Hydrated",
                    message = "Drink water before, during, and after training to keep your muscles performing.",
                ),
                "de" to TipText(title = "Genug trinken", message = "Trinke vor, während und nach dem Training Wasser, damit deine Muskeln leistungsfähig bleiben."),
                "es" to TipText(title = "Mantente hidratado", message = "Bebe agua antes, durante y después de entrenar para mantener el rendimiento muscular."),
                "fr" to TipText(title = "Restez hydraté", message = "Buvez avant, pendant et après l’entraînement pour préserver vos performances musculaires."),
                "hi" to TipText(title = "पानी पीते रहें", message = "मांसपेशियों के बेहतर प्रदर्शन के लिए प्रशिक्षण से पहले, दौरान और बाद में पानी पिएँ।"),
                "id" to TipText(title = "Jaga Hidrasi", message = "Minum air sebelum, selama, dan setelah latihan agar otot tetap bekerja optimal."),
                "pt" to TipText(title = "Mantenha-se hidratado", message = "Beba água antes, durante e depois do treino para manter o desempenho muscular."),
                "ar" to TipText(title = "حافظ على الترطيب", message = "اشرب الماء قبل التمرين وأثناءه وبعده للحفاظ على أداء عضلاتك."),
            ),
        ),
        TipModel(
            id = 2,
            texts = mapOf(
                "en" to TipText(
                    title = "Warm Up First",
                    message = "Five minutes of light movement prepares your joints and lowers injury risk.",
                ),
                "de" to TipText(title = "Erst aufwärmen", message = "Fünf Minuten leichte Bewegung bereiten deine Gelenke vor und senken das Verletzungsrisiko."),
                "es" to TipText(title = "Calienta primero", message = "Cinco minutos de movimiento suave preparan tus articulaciones y reducen el riesgo de lesión."),
                "fr" to TipText(title = "Échauffez-vous d’abord", message = "Cinq minutes de mouvements légers préparent vos articulations et réduisent le risque de blessure."),
                "hi" to TipText(title = "पहले वार्म-अप", message = "पाँच मिनट की हल्की गतिविधि जोड़ों को तैयार करती है और चोट का खतरा घटाती है।"),
                "id" to TipText(title = "Pemanasan Dulu", message = "Lima menit gerakan ringan menyiapkan sendi dan mengurangi risiko cedera."),
                "pt" to TipText(title = "Aqueça primeiro", message = "Cinco minutos de movimento leve preparam as articulações e reduzem o risco de lesão."),
                "ar" to TipText(title = "ابدأ بالإحماء", message = "خمس دقائق من الحركة الخفيفة تهيئ مفاصلك وتقلل خطر الإصابة."),
            ),
        ),
        TipModel(
            id = 3,
            texts = mapOf(
                "en" to TipText(
                    title = "Sleep Well",
                    message = "Seven to nine hours of sleep is when your body actually builds strength.",
                ),
                "de" to TipText(title = "Gut schlafen", message = "Sieben bis neun Stunden Schlaf geben deinem Körper Zeit, Kraft aufzubauen."),
                "es" to TipText(title = "Duerme bien", message = "Dormir entre siete y nueve horas permite que tu cuerpo desarrolle fuerza."),
                "fr" to TipText(title = "Dormez bien", message = "Sept à neuf heures de sommeil permettent à votre corps de développer sa force."),
                "hi" to TipText(title = "अच्छी नींद लें", message = "सात से नौ घंटे की नींद में आपका शरीर वास्तव में ताकत बनाता है।"),
                "id" to TipText(title = "Tidur Cukup", message = "Tidur tujuh hingga sembilan jam membantu tubuh benar-benar membangun kekuatan."),
                "pt" to TipText(title = "Durma bem", message = "Dormir de sete a nove horas permite que o corpo realmente desenvolva força."),
                "ar" to TipText(title = "نم جيداً", message = "يساعد النوم من سبع إلى تسع ساعات جسمك على بناء القوة فعلياً."),
            ),
        ),
        TipModel(
            id = 4,
            texts = mapOf(
                "en" to TipText(
                    title = "Progress Gradually",
                    message = "Add a little weight or time each week instead of jumping ahead too fast.",
                ),
                "de" to TipText(title = "Langsam steigern", message = "Steigere Gewicht oder Dauer jede Woche ein wenig, statt zu schnell voranzugehen."),
                "es" to TipText(title = "Progresa poco a poco", message = "Añade un poco de peso o tiempo cada semana en vez de avanzar demasiado rápido."),
                "fr" to TipText(title = "Progressez graduellement", message = "Ajoutez un peu de charge ou de temps chaque semaine sans avancer trop vite."),
                "hi" to TipText(title = "धीरे-धीरे बढ़ें", message = "बहुत तेज़ आगे बढ़ने के बजाय हर सप्ताह थोड़ा समय या भार बढ़ाएँ।"),
                "id" to TipText(title = "Tingkatkan Bertahap", message = "Tambah sedikit beban atau waktu setiap minggu tanpa terburu-buru."),
                "pt" to TipText(title = "Progrida aos poucos", message = "Aumente um pouco o peso ou o tempo a cada semana sem avançar depressa demais."),
                "ar" to TipText(title = "تقدم تدريجياً", message = "زد الوزن أو الوقت قليلاً كل أسبوع بدلاً من التقدم بسرعة كبيرة."),
            ),
        ),
        TipModel(
            id = 5,
            texts = mapOf(
                "en" to TipText(
                    title = "Rest Days Matter",
                    message = "Recovery days let your muscles repair and make the next workout stronger.",
                ),
                "de" to TipText(title = "Ruhetage zählen", message = "Erholungstage lassen deine Muskeln regenerieren und stärken dein nächstes Training."),
                "es" to TipText(title = "Descansar importa", message = "Los días de recuperación reparan tus músculos y mejoran el siguiente entrenamiento."),
                "fr" to TipText(title = "Le repos compte", message = "Les jours de récupération réparent vos muscles et renforcent la séance suivante."),
                "hi" to TipText(title = "आराम भी ज़रूरी", message = "आराम के दिन मांसपेशियों को ठीक करके अगली कसरत को बेहतर बनाते हैं।"),
                "id" to TipText(title = "Istirahat Itu Penting", message = "Hari pemulihan memperbaiki otot dan membuat latihan berikutnya lebih kuat."),
                "pt" to TipText(title = "Descanso importa", message = "Os dias de recuperação reparam os músculos e fortalecem o próximo treino."),
                "ar" to TipText(title = "الراحة مهمة", message = "تسمح أيام التعافي لعضلاتك بالإصلاح وتقوي تمرينك التالي."),
            ),
        ),
        TipModel(
            id = 6,
            texts = mapOf(
                "en" to TipText(
                    title = "Fuel Your Body",
                    message = "Eat protein and carbs within two hours after training to speed up recovery.",
                ),
                "de" to TipText(title = "Energie tanken", message = "Iss innerhalb von zwei Stunden nach dem Training Eiweiß und Kohlenhydrate für eine schnellere Erholung."),
                "es" to TipText(title = "Alimenta tu cuerpo", message = "Come proteínas y carbohidratos en las dos horas posteriores al entrenamiento para recuperarte antes."),
                "fr" to TipText(title = "Nourrissez votre corps", message = "Mangez protéines et glucides dans les deux heures après l’entraînement pour mieux récupérer."),
                "hi" to TipText(title = "शरीर को पोषण दें", message = "तेज़ रिकवरी के लिए प्रशिक्षण के दो घंटे के भीतर प्रोटीन और कार्ब्स खाएँ।"),
                "id" to TipText(title = "Isi Energi Tubuh", message = "Makan protein dan karbohidrat dalam dua jam setelah latihan agar pulih lebih cepat."),
                "pt" to TipText(title = "Abasteça o corpo", message = "Consuma proteínas e carboidratos até duas horas após o treino para acelerar a recuperação."),
                "ar" to TipText(title = "غذِّ جسمك", message = "تناول البروتين والكربوهيدرات خلال ساعتين بعد التمرين لتسريع التعافي."),
            ),
        ),
    )

    fun observeAll(): Flow<List<TipModel>> {
        return flowOf(tips)
    }
}
