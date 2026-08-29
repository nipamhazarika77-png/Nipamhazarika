package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class UserRole {
    ADMIN, TEACHER, STUDENT, PARENT, GUEST
}

@Entity(tableName = "users")
data class UserProfile(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: UserRole,
    val batchId: String = "",
    val batchName: String = "",
    val rollNo: String = "",
    val parentPhone: String = "",
    val avatarColor: Long = 0xFF1E3A8A
)

@Entity(tableName = "batches")
data class BatchEntity(
    @PrimaryKey val id: String,
    val name: String,
    val classLevel: String,
    val roomNo: String,
    val timing: String,
    val studentCount: Int = 0
)

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey val id: String,
    val batchId: String,
    val name: String,
    val teacherName: String,
    val code: String
)

@Entity(tableName = "notices")
data class NoticeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val titleAs: String,
    val description: String,
    val descriptionAs: String,
    val category: String, // "Important", "Exam", "Fees", "Holiday", "Classes"
    val date: String,
    val batchId: String = "All",
    val isImportant: Boolean = false
)

@Entity(tableName = "study_materials")
data class StudyMaterialEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val titleAs: String,
    val subject: String,
    val classLevel: String,
    val type: String, // "PDF", "VIDEO", "NOTES", "PYQ", "MCQ_SET"
    val urlOrContent: String,
    val summary: String,
    val uploadDate: String,
    val authorTeacher: String,
    val fileSize: String = "2.4 MB",
    val isDownloaded: Boolean = false
)

@Entity(tableName = "online_exams")
data class OnlineExamEntity(
    @PrimaryKey val id: String,
    val title: String,
    val titleAs: String,
    val subject: String,
    val classLevel: String,
    val durationMinutes: Int,
    val totalMarks: Int,
    val scheduledDate: String,
    val isPublished: Boolean = true,
    val questionsJson: String // Serialized questions list
)

@Entity(tableName = "exam_results")
data class ExamResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val examId: String,
    val examTitle: String,
    val studentId: String,
    val studentName: String,
    val rollNo: String,
    val batchId: String,
    val score: Int,
    val totalMarks: Int,
    val percentage: Float,
    val grade: String,
    val submittedAt: String,
    val answersReviewJson: String = ""
)

@Entity(tableName = "attendance")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, // YYYY-MM-DD
    val studentId: String,
    val studentName: String,
    val rollNo: String,
    val batchId: String,
    val status: String, // "PRESENT", "ABSENT", "LATE"
    val markedBy: String = "Faculty"
)

@Entity(tableName = "fees")
data class FeeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: String,
    val studentName: String,
    val rollNo: String,
    val batchId: String,
    val month: String,
    val amount: Double,
    val status: String, // "PAID", "DUE", "OVERDUE"
    val dueDate: String,
    val paymentDate: String = "",
    val transactionId: String = "",
    val receiptNo: String = ""
)

@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val batchId: String,
    val batchName: String,
    val dayOfWeek: String, // "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    val subject: String,
    val teacherName: String,
    val timeSlot: String,
    val room: String
)

@Entity(tableName = "transport")
data class TransportEntity(
    @PrimaryKey val id: String,
    val busNumber: String,
    val routeTitle: String,
    val driverName: String,
    val driverPhone: String,
    val currentLatitude: Double,
    val currentLongitude: Double,
    val stopsJson: String,
    val morningPickupTime: String,
    val eveningDropTime: String,
    val status: String = "On Route"
)

@Entity(tableName = "admissions")
data class AdmissionInquiryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentName: String,
    val parentName: String,
    val phone: String,
    val email: String,
    val targetClass: String,
    val selectedCourse: String,
    val previousSchool: String,
    val address: String,
    val status: String = "NEW",
    val date: String
)

@Entity(tableName = "feedback")
data class FeedbackEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val senderName: String,
    val role: String,
    val rating: Int,
    val category: String,
    val comment: String,
    val date: String
)

data class Question(
    val id: Int,
    val questionText: String,
    val questionTextAs: String = "",
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String = ""
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val isThinking: Boolean = false,
    val subjectTag: String = "General",
    val isError: Boolean = false
)

data class AiPromptTopic(
    val id: String,
    val title: String,
    val titleAs: String,
    val prompt: String,
    val promptAs: String,
    val subject: String,
    val iconName: String
)

