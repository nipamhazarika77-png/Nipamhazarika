package com.example.data.repository

import com.example.data.local.CoachingDao
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

class CoachingRepository(private val dao: CoachingDao) {

    val allUsers: Flow<List<UserProfile>> = dao.getAllUsers()
    val allBatches: Flow<List<BatchEntity>> = dao.getAllBatches()
    val allSubjects: Flow<List<SubjectEntity>> = dao.getAllSubjects()
    val allNotices: Flow<List<NoticeEntity>> = dao.getAllNotices()
    val allMaterials: Flow<List<StudyMaterialEntity>> = dao.getAllMaterials()
    val allExams: Flow<List<OnlineExamEntity>> = dao.getAllExams()
    val allResults: Flow<List<ExamResultEntity>> = dao.getAllResults()
    val allAttendance: Flow<List<AttendanceEntity>> = dao.getAllAttendance()
    val allFees: Flow<List<FeeEntity>> = dao.getAllFees()
    val allSchedules: Flow<List<ScheduleEntity>> = dao.getAllSchedules()
    val allTransport: Flow<List<TransportEntity>> = dao.getAllTransport()
    val allAdmissions: Flow<List<AdmissionInquiryEntity>> = dao.getAllAdmissions()
    val allFeedback: Flow<List<FeedbackEntity>> = dao.getAllFeedback()

    fun getStudentResults(studentId: String): Flow<List<ExamResultEntity>> = dao.getResultsByStudent(studentId)
    fun getStudentAttendance(studentId: String): Flow<List<AttendanceEntity>> = dao.getAttendanceByStudent(studentId)
    fun getStudentFees(studentId: String): Flow<List<FeeEntity>> = dao.getFeesByStudent(studentId)

    // User Operations
    suspend fun insertUser(user: UserProfile) = dao.insertUser(user)
    suspend fun deleteUser(id: String) = dao.deleteUser(id)

    // Batch Operations
    suspend fun insertBatch(batch: BatchEntity) = dao.insertBatch(batch)
    suspend fun deleteBatch(id: String) = dao.deleteBatch(id)

    // Subject Operations
    suspend fun insertSubject(subject: SubjectEntity) = dao.insertSubjects(listOf(subject))

    // Notice Operations
    suspend fun insertNotice(notice: NoticeEntity) = dao.insertNotice(notice)
    suspend fun deleteNotice(id: Int) = dao.deleteNotice(id)

    // Study Material Operations
    suspend fun insertMaterial(material: StudyMaterialEntity) = dao.insertMaterial(material)
    suspend fun updateMaterial(material: StudyMaterialEntity) = dao.updateMaterial(material)
    suspend fun deleteMaterial(id: Int) = dao.deleteMaterial(id)

    // Exam Operations
    suspend fun insertExam(exam: OnlineExamEntity) = dao.insertExam(exam)
    suspend fun deleteExam(id: String) = dao.deleteExam(id)
    suspend fun submitExamResult(result: ExamResultEntity) = dao.insertResult(result)

    // Attendance Operations
    suspend fun insertAttendance(record: AttendanceEntity) = dao.insertAttendance(record)
    suspend fun insertAttendanceList(records: List<AttendanceEntity>) = dao.insertAttendanceList(records)

    // Fee Operations
    suspend fun insertFee(fee: FeeEntity) = dao.insertFee(fee)
    suspend fun updateFee(fee: FeeEntity) = dao.updateFee(fee)

    // Schedule Operations
    suspend fun insertSchedule(schedule: ScheduleEntity) = dao.insertSchedules(listOf(schedule))

    // Transport Operations
    suspend fun updateTransport(transport: TransportEntity) = dao.updateTransport(transport)

    // Admission Operations
    suspend fun insertAdmission(inquiry: AdmissionInquiryEntity) = dao.insertAdmission(inquiry)
    suspend fun updateAdmissionStatus(id: Int, status: String) = dao.updateAdmissionStatus(id, status)

    // Feedback Operations
    suspend fun insertFeedback(feedback: FeedbackEntity) = dao.insertFeedback(feedback)
}
