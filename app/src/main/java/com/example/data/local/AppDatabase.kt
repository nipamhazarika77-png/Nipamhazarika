package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.AdmissionInquiryEntity
import com.example.data.model.AttendanceEntity
import com.example.data.model.BatchEntity
import com.example.data.model.ExamResultEntity
import com.example.data.model.FeeEntity
import com.example.data.model.FeedbackEntity
import com.example.data.model.NoticeEntity
import com.example.data.model.OnlineExamEntity
import com.example.data.model.ScheduleEntity
import com.example.data.model.StudyMaterialEntity
import com.example.data.model.SubjectEntity
import com.example.data.model.TransportEntity
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserProfile::class,
        BatchEntity::class,
        SubjectEntity::class,
        NoticeEntity::class,
        StudyMaterialEntity::class,
        OnlineExamEntity::class,
        ExamResultEntity::class,
        AttendanceEntity::class,
        FeeEntity::class,
        ScheduleEntity::class,
        TransportEntity::class,
        AdmissionInquiryEntity::class,
        FeedbackEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coachingDao(): CoachingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pragyan_coaching_db"
                )
                .addCallback(DatabaseCallback(scope))
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.coachingDao())
                    }
                }
            }
        }

        suspend fun populateInitialData(dao: CoachingDao) {
            // Seed Users
            val users = listOf(
                UserProfile(
                    id = "student_1",
                    name = "Abhinav Bezbaruah",
                    email = "abhinav.b@gmail.com",
                    phone = "+91 98640 12345",
                    role = UserRole.STUDENT,
                    batchId = "batch_10_lakshya",
                    batchName = "Class 10 - Lakshya Batch",
                    rollNo = "PR-1024",
                    parentPhone = "+91 94350 98765",
                    avatarColor = 0xFF2563EB
                ),
                UserProfile(
                    id = "student_2",
                    name = "Priyanka Kalita",
                    email = "priyanka.k@gmail.com",
                    phone = "+91 98590 54321",
                    role = UserRole.STUDENT,
                    batchId = "batch_12_science",
                    batchName = "Class 12 - Eureka Science",
                    rollNo = "PR-1208",
                    parentPhone = "+91 94351 11223",
                    avatarColor = 0xFF7C3AED
                ),
                UserProfile(
                    id = "teacher_1",
                    name = "Dr. Bhaskar Sarma",
                    email = "dr.sarma@pragyan.edu",
                    phone = "+91 98641 99887",
                    role = UserRole.TEACHER,
                    batchId = "batch_10_lakshya",
                    batchName = "Class 10 & 12 Science",
                    rollNo = "FAC-01",
                    parentPhone = "",
                    avatarColor = 0xFF059669
                ),
                UserProfile(
                    id = "parent_1",
                    name = "Dipak Bezbaruah (Parent of Abhinav)",
                    email = "dipak.bezbaruah@gmail.com",
                    phone = "+91 94350 98765",
                    role = UserRole.PARENT,
                    batchId = "batch_10_lakshya",
                    batchName = "Class 10 - Lakshya Batch",
                    rollNo = "PR-1024",
                    parentPhone = "+91 94350 98765",
                    avatarColor = 0xFFD97706
                ),
                UserProfile(
                    id = "admin_1",
                    name = "Admin / Director (G. K. Baruah)",
                    email = "director@pragyanacademy.com",
                    phone = "+91 98640 00001",
                    role = UserRole.ADMIN,
                    batchId = "All",
                    batchName = "Administration",
                    rollNo = "ADM-001",
                    parentPhone = "",
                    avatarColor = 0xFFDC2626
                )
            )
            dao.insertUsers(users)

            // Seed Batches
            val batches = listOf(
                BatchEntity("batch_9_foundation", "Class 9 - Foundation Batch", "Class 9", "Room 101", "3:30 PM - 6:00 PM", 38),
                BatchEntity("batch_10_lakshya", "Class 10 - Lakshya HSLC / CBSE", "Class 10", "Room 102", "4:00 PM - 7:00 PM", 45),
                BatchEntity("batch_11_science", "Class 11 - Science Pinnacle", "Class 11", "Room 201", "8:00 AM - 12:30 PM", 40),
                BatchEntity("batch_12_science", "Class 12 - Eureka Science & Boards", "Class 12", "Room 202", "8:00 AM - 1:00 PM", 42),
                BatchEntity("batch_neet_jee", "NEET / JEE Droppers & Achievers", "Competitive", "Hall A", "9:00 AM - 3:30 PM", 55)
            )
            dao.insertBatches(batches)

            // Seed Subjects
            val subjects = listOf(
                SubjectEntity("sub_1", "batch_10_lakshya", "General Mathematics (গণিত)", "Dr. Bhaskar Sarma", "MATH-10"),
                SubjectEntity("sub_2", "batch_10_lakshya", "General Science (বিজ্ঞান)", "Prof. Nilav Goswami", "SCI-10"),
                SubjectEntity("sub_3", "batch_10_lakshya", "English & Grammar", "Mrs. Rumi Borah", "ENG-10"),
                SubjectEntity("sub_4", "batch_10_lakshya", "Assamese MIL (অসমীয়া সাহিত্য)", "Dr. Pranab Saikia", "ASM-10"),
                SubjectEntity("sub_5", "batch_10_lakshya", "Social Science (সমাজ বিজ্ঞান)", "Mr. Ankur Deka", "SS-10"),
                SubjectEntity("sub_6", "batch_12_science", "Physics (পদাৰ্থ বিজ্ঞান)", "Dr. Bhaskar Sarma", "PHY-12"),
                SubjectEntity("sub_7", "batch_12_science", "Chemistry (ৰসায়ন বিজ্ঞান)", "Prof. Nilav Goswami", "CHEM-12"),
                SubjectEntity("sub_8", "batch_12_science", "Biology (জীৱবিজ্ঞান)", "Dr. Anamika Das", "BIO-12")
            )
            dao.insertSubjects(subjects)

            // Seed Notices
            val notices = listOf(
                NoticeEntity(
                    title = "Monthly Unit Test - 2 Commencing from Monday",
                    titleAs = "সোমবাৰৰ পৰা মাহেকীয়া ইউনিট টেষ্ট - ২ আৰম্ভ হ'ব",
                    description = "All students of Class 9, 10 & 12 must report at 8:30 AM with their roll cards. Test syllabus is up to Chapter 5.",
                    descriptionAs = "৯ম, ১০ম আৰু ১২শ শ্ৰেণীৰ সকলো ছাত্ৰ-ছাত্ৰীক ৰোল কাৰ্ডসহ পুৱা ৮:৩০ বজাত উপস্থিত থাকিবলৈ জনোৱা হ'ল।",
                    category = "Exam",
                    date = "28 Aug 2026",
                    batchId = "All",
                    isImportant = true
                ),
                NoticeEntity(
                    title = "Notice for Tuition Fee Payment for September 2026",
                    titleAs = "ছেপ্টেম্বৰ মাহৰ টিউচন মাচুল পৰিশোধৰ জাননী",
                    description = "Tuition fees for September must be cleared by 10th Sept via online UPI or office cash counter to avoid late fee.",
                    descriptionAs = "ছেপ্টেম্বৰ মাহৰ টিউচন মাচুল ১০ ছেপ্টেম্বৰৰ ভিতৰত অনলাইন বা অফিচত জমা দিয়াৰ বাবে অনুৰোধ জনোৱা হ'ল।",
                    category = "Fees",
                    date = "26 Aug 2026",
                    batchId = "All",
                    isImportant = false
                ),
                NoticeEntity(
                    title = "Special Doubt Clearing Session & Assamese Medium Notes",
                    titleAs = "বিশেষ সন্দেহ দূৰীকৰণ পাঠদান আৰু অসমীয়া মাধ্যমৰ নোট বিতৰণ",
                    description = "Special extra class for Class 10 Science (Electricity & Carbon Compounds) scheduled this Saturday at 2:00 PM.",
                    descriptionAs = "দশম শ্ৰেণীৰ বিজ্ঞান বিষয়ৰ বিশেষ অতিৰিক্ত পাঠদান এই শনিবাৰে আবেলি ২ বজাত অনুষ্ঠিত হ'ব।",
                    category = "Classes",
                    date = "24 Aug 2026",
                    batchId = "batch_10_lakshya",
                    isImportant = true
                )
            )
            dao.insertNotices(notices)

            // Seed Study Materials
            val materials = listOf(
                StudyMaterialEntity(
                    title = "Class 10 Science: Chemical Reactions & Equations (সম্পূৰ্ণ নোট)",
                    titleAs = "দশম শ্ৰেণী বিজ্ঞান: ৰাসায়নিক বিক্ৰিয়া আৰু সমীকৰণ",
                    subject = "General Science",
                    classLevel = "Class 10",
                    type = "PDF",
                    urlOrContent = "https://pragyan.edu/notes/chem_reactions_class10.pdf",
                    summary = "Detailed chapter explanation with balanced equations, NCERT exercises & previous board question answers in Assamese & English.",
                    uploadDate = "25 Aug 2026",
                    authorTeacher = "Prof. Nilav Goswami",
                    fileSize = "3.2 MB",
                    isDownloaded = true
                ),
                StudyMaterialEntity(
                    title = "Class 10 Maths: Quadratic Equations & Trigonometry Formula Sheet",
                    titleAs = "দশম শ্ৰেণী গণিত: দ্বিঘাত সমীকৰণ আৰু ত্ৰিকোণমিতিৰ সূত্ৰাৱলী",
                    subject = "General Mathematics",
                    classLevel = "Class 10",
                    type = "PDF",
                    urlOrContent = "https://pragyan.edu/notes/trigo_quad_formula.pdf",
                    summary = "Handwritten master formula sheet with shortcut tricks for HSLC and Board examination 2026.",
                    uploadDate = "22 Aug 2026",
                    authorTeacher = "Dr. Bhaskar Sarma",
                    fileSize = "1.8 MB",
                    isDownloaded = false
                ),
                StudyMaterialEntity(
                    title = "Video Masterclass: Ray Optics & Lens Formula with Live Derivation",
                    titleAs = "ভিডিঅ' পাঠদান: পোহৰৰ প্ৰতিসৰণ আৰু লেন্সৰ সূত্ৰ নিৰ্ণয়",
                    subject = "Physics",
                    classLevel = "Class 12",
                    type = "VIDEO",
                    urlOrContent = "https://youtu.be/pragyan_optics_ch09",
                    summary = "Complete 45-minute high-definition video lecture covering sign convention, spherical lenses and magnifying power.",
                    uploadDate = "20 Aug 2026",
                    authorTeacher = "Dr. Bhaskar Sarma",
                    fileSize = "120 MB",
                    isDownloaded = false
                ),
                StudyMaterialEntity(
                    title = "Class 10 HSLC 10 Years Previous Question Papers (Solved)",
                    titleAs = "বিগত ১০ বছৰৰ হাইস্কুল শিক্ষান্ত পৰীক্ষাৰ সমাধান কৰা প্ৰশ্নোত্তৰ",
                    subject = "General Science & Maths",
                    classLevel = "Class 10",
                    type = "PYQ",
                    urlOrContent = "https://pragyan.edu/pyq/hslc_10_years_solved.pdf",
                    summary = "Official SEBA & CBSE board question papers from 2015 to 2025 with complete model answers.",
                    uploadDate = "18 Aug 2026",
                    authorTeacher = "Faculty Panel",
                    fileSize = "6.5 MB",
                    isDownloaded = true
                ),
                StudyMaterialEntity(
                    title = "Class 10 Assamese MIL: বৰগীত আৰু কবিতাৰ গুৰুত্বপূৰ্ণ প্ৰশ্নোত্তৰ",
                    titleAs = "দশম শ্ৰেণী অসমীয়া: বৰগীত আৰু কবিতা সংকলনৰ প্ৰশ্নোত্তৰ",
                    subject = "Assamese MIL",
                    classLevel = "Class 10",
                    type = "NOTES",
                    urlOrContent = "https://pragyan.edu/notes/assamese_mil_bargit.pdf",
                    summary = "শংকৰদেৱ-মাধৱদেৱৰ বৰগীতৰ ভাবাৰ্থ, ব্যাখ্যা আৰু ব্যাকৰণৰ প্ৰয়োজনীয় প্ৰশ্নসমূহ।",
                    uploadDate = "15 Aug 2026",
                    authorTeacher = "Dr. Pranab Saikia",
                    fileSize = "2.1 MB",
                    isDownloaded = false
                )
            )
            dao.insertMaterials(materials)

            // Seed Online Exams
            val exam1Questions = """
                [
                  {"id": 1, "questionText": "What is the SI unit of electric current?", "questionTextAs": "বিদ্যুৎ প্ৰবাহৰ SI একক কি?", "options": ["Volt (ভল্ট)", "Ampere (এম্পিয়াৰ)", "Ohm (ওম)", "Watt (ৱাট)"], "correctIndex": 1, "explanation": "Ampere (A) is the SI unit of electric current named after André-Marie Ampère."},
                  {"id": 2, "questionText": "Which gas is evolved when zinc granules react with dilute sulphuric acid?", "questionTextAs": "জিংকৰ সৈতে লঘু ছালফিউৰিক এচিডৰ বিক্ৰিয়াত কি গেছ উৎপন্ন হয়?", "options": ["Oxygen (অক্সিজেন)", "Carbon Dioxide (কাৰ্বন ডাই-অক্সাইড)", "Hydrogen (হাইড্ৰজেন)", "Nitrogen (নাইট্ৰজেন)"], "correctIndex": 2, "explanation": "Zn + H2SO4 -> ZnSO4 + H2 (Hydrogen gas is evolved with pop sound)."},
                  {"id": 3, "questionText": "The focal length of a spherical mirror with radius of curvature 30 cm is:", "questionTextAs": "৩০ ছেঃমিঃ ভাঁজ ব্যাসাৰ্ধ থকা এখন গোলাকাৰ দাপোণৰ ফ'কাছ দৈৰ্ঘ্য হ'ব:", "options": ["15 cm", "60 cm", "30 cm", "7.5 cm"], "correctIndex": 0, "explanation": "Focal length f = R / 2 = 30 / 2 = 15 cm."},
                  {"id": 4, "questionText": "What is the powerhouse of the cell?", "questionTextAs": "কোষৰ শক্তিঘৰ বুলি কাক কোৱা হয়?", "options": ["Nucleus (নিউক্লিয়াছ)", "Ribosome (ৰাইবোজম)", "Mitochondria (মাইট'কন্ড্ৰিয়া)", "Golgi apparatus (গলগি বডি)"], "correctIndex": 2, "explanation": "Mitochondria produces cellular energy in the form of ATP."}
                ]
            """.trimIndent()

            val exam2Questions = """
                [
                  {"id": 1, "questionText": "If roots of ax^2 + bx + c = 0 are equal, then discriminant D is:", "questionTextAs": "ax^2 + bx + c = 0 সমীকৰণৰ মূল দুটা সমান হ'লে ভেদ নিৰূপক D হ'ব:", "options": ["D > 0", "D = 0", "D < 0", "D >= 0"], "correctIndex": 1, "explanation": "Equal real roots occur when b^2 - 4ac = 0."},
                  {"id": 2, "questionText": "The value of sin 30° + cos 60° is:", "questionTextAs": "sin 30° + cos 60° ৰ মান কিমান?", "options": ["1/2", "1", "sqrt(3)/2", "0"], "correctIndex": 1, "explanation": "sin 30° = 1/2, cos 60° = 1/2 => 1/2 + 1/2 = 1."},
                  {"id": 3, "questionText": "The n-th term of an AP is given by an = 3 + 4n. The common difference is:", "questionTextAs": "এটা সমান্তৰ প্ৰগতিৰ n-তম পদ an = 3 + 4n হ'লে সাধাৰণ অন্তৰ হ'ব:", "options": ["3", "4", "7", "1"], "correctIndex": 1, "explanation": "d = a2 - a1 = (3 + 8) - (3 + 4) = 11 - 7 = 4."}
                ]
            """.trimIndent()

            val exams = listOf(
                OnlineExamEntity(
                    id = "exam_10_sci_mock",
                    title = "Class 10 Science Mega Mock Test 2026",
                    titleAs = "দশম শ্ৰেণী সাধাৰণ বিজ্ঞান মেগা মক টেষ্ট",
                    subject = "General Science",
                    classLevel = "Class 10",
                    durationMinutes = 15,
                    totalMarks = 20,
                    scheduledDate = "29 Aug 2026",
                    isPublished = true,
                    questionsJson = exam1Questions
                ),
                OnlineExamEntity(
                    id = "exam_10_math_trigo",
                    title = "Class 10 Mathematics Chapter-wise Quiz: Trigonometry & AP",
                    titleAs = "দশম শ্ৰেণী গণিত অধ্যায়ভিত্তিক কুইজ: ত্ৰিকোণমিতি আৰু সমান্তৰ প্ৰগতি",
                    subject = "General Mathematics",
                    classLevel = "Class 10",
                    durationMinutes = 10,
                    totalMarks = 15,
                    scheduledDate = "30 Aug 2026",
                    isPublished = true,
                    questionsJson = exam2Questions
                )
            )
            dao.insertExams(exams)

            // Seed Exam Results
            val results = listOf(
                ExamResultEntity(
                    examId = "exam_10_sci_mock",
                    examTitle = "Class 10 Science Mega Mock Test 2026",
                    studentId = "student_1",
                    studentName = "Abhinav Bezbaruah",
                    rollNo = "PR-1024",
                    batchId = "batch_10_lakshya",
                    score = 18,
                    totalMarks = 20,
                    percentage = 90.0f,
                    grade = "A+",
                    submittedAt = "27 Aug 2026, 04:30 PM",
                    answersReviewJson = ""
                ),
                ExamResultEntity(
                    examId = "exam_10_sci_mock",
                    examTitle = "Class 10 Science Mega Mock Test 2026",
                    studentId = "student_2",
                    studentName = "Priyanka Kalita",
                    rollNo = "PR-1208",
                    batchId = "batch_10_lakshya",
                    score = 20,
                    totalMarks = 20,
                    percentage = 100.0f,
                    grade = "Outstanding",
                    submittedAt = "27 Aug 2026, 04:25 PM",
                    answersReviewJson = ""
                )
            )
            dao.insertResults(results)

            // Seed Attendance
            val attendanceList = listOf(
                AttendanceEntity(date = "2026-08-28", studentId = "student_1", studentName = "Abhinav Bezbaruah", rollNo = "PR-1024", batchId = "batch_10_lakshya", status = "PRESENT", markedBy = "Dr. Bhaskar Sarma"),
                AttendanceEntity(date = "2026-08-27", studentId = "student_1", studentName = "Abhinav Bezbaruah", rollNo = "PR-1024", batchId = "batch_10_lakshya", status = "PRESENT", markedBy = "Prof. Nilav Goswami"),
                AttendanceEntity(date = "2026-08-26", studentId = "student_1", studentName = "Abhinav Bezbaruah", rollNo = "PR-1024", batchId = "batch_10_lakshya", status = "LATE", markedBy = "Mrs. Rumi Borah"),
                AttendanceEntity(date = "2026-08-25", studentId = "student_1", studentName = "Abhinav Bezbaruah", rollNo = "PR-1024", batchId = "batch_10_lakshya", status = "PRESENT", markedBy = "Dr. Bhaskar Sarma"),
                AttendanceEntity(date = "2026-08-24", studentId = "student_1", studentName = "Abhinav Bezbaruah", rollNo = "PR-1024", batchId = "batch_10_lakshya", status = "ABSENT", markedBy = "Dr. Pranab Saikia"),
                AttendanceEntity(date = "2026-08-28", studentId = "student_2", studentName = "Priyanka Kalita", rollNo = "PR-1208", batchId = "batch_12_science", status = "PRESENT", markedBy = "Dr. Bhaskar Sarma")
            )
            dao.insertAttendanceList(attendanceList)

            // Seed Fees
            val fees = listOf(
                FeeEntity(
                    studentId = "student_1",
                    studentName = "Abhinav Bezbaruah",
                    rollNo = "PR-1024",
                    batchId = "batch_10_lakshya",
                    month = "August 2026",
                    amount = 1800.0,
                    status = "PAID",
                    dueDate = "10 Aug 2026",
                    paymentDate = "05 Aug 2026",
                    transactionId = "UPI/26080519284/PRAGYAN",
                    receiptNo = "PRG-2026-08-1024"
                ),
                FeeEntity(
                    studentId = "student_1",
                    studentName = "Abhinav Bezbaruah",
                    rollNo = "PR-1024",
                    batchId = "batch_10_lakshya",
                    month = "September 2026",
                    amount = 1800.0,
                    status = "DUE",
                    dueDate = "10 Sep 2026",
                    paymentDate = "",
                    transactionId = "",
                    receiptNo = ""
                ),
                FeeEntity(
                    studentId = "student_2",
                    studentName = "Priyanka Kalita",
                    rollNo = "PR-1208",
                    batchId = "batch_12_science",
                    month = "August 2026",
                    amount = 2500.0,
                    status = "PAID",
                    dueDate = "10 Aug 2026",
                    paymentDate = "07 Aug 2026",
                    transactionId = "UPI/26080788319/PRAGYAN",
                    receiptNo = "PRG-2026-08-1208"
                )
            )
            dao.insertFees(fees)

            // Seed Timetable / Schedules
            val schedules = listOf(
                ScheduleEntity(batchId = "batch_10_lakshya", batchName = "Class 10 - Lakshya", dayOfWeek = "Monday", subject = "Mathematics (গণিত)", teacherName = "Dr. Bhaskar Sarma", timeSlot = "04:00 PM - 05:30 PM", room = "Room 102"),
                ScheduleEntity(batchId = "batch_10_lakshya", batchName = "Class 10 - Lakshya", dayOfWeek = "Monday", subject = "General Science (বিজ্ঞান)", teacherName = "Prof. Nilav Goswami", timeSlot = "05:30 PM - 07:00 PM", room = "Room 102"),
                ScheduleEntity(batchId = "batch_10_lakshya", batchName = "Class 10 - Lakshya", dayOfWeek = "Tuesday", subject = "Assamese MIL (অসমীয়া)", teacherName = "Dr. Pranab Saikia", timeSlot = "04:00 PM - 05:30 PM", room = "Room 102"),
                ScheduleEntity(batchId = "batch_10_lakshya", batchName = "Class 10 - Lakshya", dayOfWeek = "Tuesday", subject = "English & Grammar", teacherName = "Mrs. Rumi Borah", timeSlot = "05:30 PM - 07:00 PM", room = "Room 102"),
                ScheduleEntity(batchId = "batch_10_lakshya", batchName = "Class 10 - Lakshya", dayOfWeek = "Wednesday", subject = "Mathematics (Doubt Class)", teacherName = "Dr. Bhaskar Sarma", timeSlot = "04:00 PM - 05:30 PM", room = "Room 102"),
                ScheduleEntity(batchId = "batch_10_lakshya", batchName = "Class 10 - Lakshya", dayOfWeek = "Wednesday", subject = "Social Science (সমাজ বিজ্ঞান)", teacherName = "Mr. Ankur Deka", timeSlot = "05:30 PM - 07:00 PM", room = "Room 102"),
                ScheduleEntity(batchId = "batch_10_lakshya", batchName = "Class 10 - Lakshya", dayOfWeek = "Thursday", subject = "Science Numerical & Lab", teacherName = "Prof. Nilav Goswami", timeSlot = "04:00 PM - 06:00 PM", room = "Science Lab 1"),
                ScheduleEntity(batchId = "batch_10_lakshya", batchName = "Class 10 - Lakshya", dayOfWeek = "Friday", subject = "Weekly Mock MCQ Test", teacherName = "Faculty Team", timeSlot = "04:30 PM - 06:30 PM", room = "Exam Hall B"),
                ScheduleEntity(batchId = "batch_10_lakshya", batchName = "Class 10 - Lakshya", dayOfWeek = "Saturday", subject = "Special Career Counselling", teacherName = "Director G.K. Baruah", timeSlot = "03:00 PM - 05:00 PM", room = "Auditorium")
            )
            dao.insertSchedules(schedules)

            // Seed Transport (Guwahati route)
            val stops = """
                [
                  {"stopName": "Khanapara Junction (খানাপাৰা)", "time": "07:15 AM / 07:15 PM"},
                  {"stopName": "Six Mile Flyover (ছিক্সমাইল)", "time": "07:25 AM / 07:05 PM"},
                  {"stopName": "Ganeshguri Point (গণেশগুৰি)", "time": "07:35 AM / 06:55 PM"},
                  {"stopName": "Bhangagarh Medical Gate (ভাঙাগড়)", "time": "07:45 AM / 06:45 PM"},
                  {"stopName": "Pragyan Academy Ulubari (কোচিং চেন্টাৰ)", "time": "07:55 AM / 06:35 PM"}
                ]
            """.trimIndent()

            val transports = listOf(
                TransportEntity(
                    id = "bus_route_1",
                    busNumber = "AS-01-EC-4492 (Bus No. 1)",
                    routeTitle = "Route A: Khanapara - Ganeshguri - Ulubari",
                    driverName = "Manoj Das (মনোজ দাস)",
                    driverPhone = "+91 94350 44556",
                    currentLatitude = 26.1550,
                    currentLongitude = 91.7780,
                    stopsJson = stops,
                    morningPickupTime = "07:15 AM - 07:55 AM",
                    eveningDropTime = "06:30 PM - 07:20 PM",
                    status = "On Schedule (নিয়মিত)"
                ),
                TransportEntity(
                    id = "bus_route_2",
                    busNumber = "AS-01-EC-7821 (Bus No. 2)",
                    routeTitle = "Route B: Jalukbari - Maligaon - Bharalumukh - Ulubari",
                    driverName = "Prabin Hazarika (প্ৰবীণ হাজৰিকা)",
                    driverPhone = "+91 98540 11223",
                    currentLatitude = 26.1680,
                    currentLongitude = 91.7250,
                    stopsJson = stops,
                    morningPickupTime = "07:05 AM - 07:50 AM",
                    eveningDropTime = "06:30 PM - 07:25 PM",
                    status = "Approaching Stop 3 (ষ্টপেজলৈ গৈ আছে)"
                )
            )
            dao.insertTransportList(transports)

            // Seed Admission Inquiries
            val admissions = listOf(
                AdmissionInquiryEntity(
                    studentName = "Gitashree Sharma",
                    parentName = "Kamal Sharma",
                    phone = "+91 98642 33445",
                    email = "kamal.sharma@gmail.com",
                    targetClass = "Class 11",
                    selectedCourse = "NEET Integrated Science Batch 2026-28",
                    previousSchool = "Holy Child School, Guwahati",
                    address = "Zoo Road, Guwahati",
                    status = "CONTACTED",
                    date = "27 Aug 2026"
                ),
                AdmissionInquiryEntity(
                    studentName = "Debojit Bora",
                    parentName = "Naren Bora",
                    phone = "+91 98591 66778",
                    email = "naren.bora@yahoo.com",
                    targetClass = "Class 10",
                    selectedCourse = "HSLC Board Super 50 Batch",
                    previousSchool = "Cotton Collegiate HS School",
                    address = "Chandmari, Guwahati",
                    status = "NEW",
                    date = "28 Aug 2026"
                )
            )
            dao.insertAdmissions(admissions)

            // Seed Feedback
            val feedbacks = listOf(
                FeedbackEntity(
                    senderName = "Abhinav Bezbaruah (Student)",
                    role = "Student",
                    rating = 5,
                    category = "Faculty & Teaching",
                    comment = "Dr. Bhaskar Sarma's Physics and Maths explanations make tough concepts super easy! The Assamese language notes are extremely helpful.",
                    date = "26 Aug 2026"
                ),
                FeedbackEntity(
                    senderName = "Dipak Bezbaruah (Parent)",
                    role = "Parent",
                    rating = 5,
                    category = "Attendance & SMS Alerts",
                    comment = "Very happy with daily attendance tracking and quick fee payment updates in the app. Clean learning environment.",
                    date = "25 Aug 2026"
                )
            )
            dao.insertFeedbackList(feedbacks)
        }
    }
}
