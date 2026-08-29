package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
import kotlinx.coroutines.flow.Flow

@Dao
interface CoachingDao {

    // Users
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<UserProfile>>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: String): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserProfile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserProfile>)

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: String)

    // Batches
    @Query("SELECT * FROM batches ORDER BY name ASC")
    fun getAllBatches(): Flow<List<BatchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatch(batch: BatchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatches(batches: List<BatchEntity>)

    @Query("DELETE FROM batches WHERE id = :id")
    suspend fun deleteBatch(id: String)

    // Subjects
    @Query("SELECT * FROM subjects ORDER BY name ASC")
    fun getAllSubjects(): Flow<List<SubjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SubjectEntity>)

    // Notices
    @Query("SELECT * FROM notices ORDER BY id DESC")
    fun getAllNotices(): Flow<List<NoticeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotice(notice: NoticeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotices(notices: List<NoticeEntity>)

    @Query("DELETE FROM notices WHERE id = :id")
    suspend fun deleteNotice(id: Int)

    // Study Materials
    @Query("SELECT * FROM study_materials ORDER BY id DESC")
    fun getAllMaterials(): Flow<List<StudyMaterialEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterial(material: StudyMaterialEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterials(materials: List<StudyMaterialEntity>)

    @Update
    suspend fun updateMaterial(material: StudyMaterialEntity)

    @Query("DELETE FROM study_materials WHERE id = :id")
    suspend fun deleteMaterial(id: Int)

    // Online Exams
    @Query("SELECT * FROM online_exams ORDER BY scheduledDate DESC")
    fun getAllExams(): Flow<List<OnlineExamEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExam(exam: OnlineExamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExams(exams: List<OnlineExamEntity>)

    @Query("DELETE FROM online_exams WHERE id = :id")
    suspend fun deleteExam(id: String)

    // Exam Results
    @Query("SELECT * FROM exam_results ORDER BY id DESC")
    fun getAllResults(): Flow<List<ExamResultEntity>>

    @Query("SELECT * FROM exam_results WHERE studentId = :studentId ORDER BY id DESC")
    fun getResultsByStudent(studentId: String): Flow<List<ExamResultEntity>>

    @Query("SELECT * FROM exam_results WHERE examId = :examId ORDER BY score DESC")
    fun getResultsByExam(examId: String): Flow<List<ExamResultEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: ExamResultEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResults(results: List<ExamResultEntity>)

    // Attendance
    @Query("SELECT * FROM attendance ORDER BY date DESC, id DESC")
    fun getAllAttendance(): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE studentId = :studentId ORDER BY date DESC")
    fun getAttendanceByStudent(studentId: String): Flow<List<AttendanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(record: AttendanceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendanceList(records: List<AttendanceEntity>)

    // Fees
    @Query("SELECT * FROM fees ORDER BY id DESC")
    fun getAllFees(): Flow<List<FeeEntity>>

    @Query("SELECT * FROM fees WHERE studentId = :studentId ORDER BY id DESC")
    fun getFeesByStudent(studentId: String): Flow<List<FeeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFee(fee: FeeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFees(fees: List<FeeEntity>)

    @Update
    suspend fun updateFee(fee: FeeEntity)

    // Schedules
    @Query("SELECT * FROM schedules ORDER BY id ASC")
    fun getAllSchedules(): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    // Transport
    @Query("SELECT * FROM transport ORDER BY busNumber ASC")
    fun getAllTransport(): Flow<List<TransportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransport(transport: TransportEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransportList(transportList: List<TransportEntity>)

    @Update
    suspend fun updateTransport(transport: TransportEntity)

    // Admissions
    @Query("SELECT * FROM admissions ORDER BY id DESC")
    fun getAllAdmissions(): Flow<List<AdmissionInquiryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdmission(inquiry: AdmissionInquiryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdmissions(inquiries: List<AdmissionInquiryEntity>)

    @Query("UPDATE admissions SET status = :status WHERE id = :id")
    suspend fun updateAdmissionStatus(id: Int, status: String)

    // Feedback
    @Query("SELECT * FROM feedback ORDER BY id DESC")
    fun getAllFeedback(): Flow<List<FeedbackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedback(feedback: FeedbackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedbackList(feedbackList: List<FeedbackEntity>)
}
