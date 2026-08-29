package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.localization.Language
import com.example.data.model.AdmissionInquiryEntity
import com.example.data.model.AttendanceEntity
import com.example.data.model.BatchEntity
import com.example.data.model.ExamResultEntity
import com.example.data.model.FeeEntity
import com.example.data.model.FeedbackEntity
import com.example.data.model.NoticeEntity
import com.example.data.model.OnlineExamEntity
import com.example.data.model.Question
import com.example.data.model.ScheduleEntity
import com.example.data.model.StudyMaterialEntity
import com.example.data.model.SubjectEntity
import com.example.data.model.TransportEntity
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import com.example.data.repository.CoachingRepository
import com.example.data.model.ChatMessage
import com.example.data.remote.GeminiApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

enum class AppScreen {
    HOME,
    STUDENT_PANEL,
    TEACHER_PANEL,
    PARENT_PANEL,
    ADMIN_PANEL,
    STUDY_MATERIALS,
    ONLINE_EXAMS,
    EXAM_RUNNER,
    FEES_PAYMENT,
    CLASS_SCHEDULE,
    TRANSPORT,
    ADMISSION_CONTACT,
    GALLERY_TESTIMONIALS,
    AI_ASSISTANT
}

class CoachingViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val repository = CoachingRepository(database.coachingDao())

    // Language & Theme State
    private val _currentLanguage = MutableStateFlow(Language.ENGLISH)
    val currentLanguage: StateFlow<Language> = _currentLanguage.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    // Navigation State
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Active User Profile (Default: Student Abhinav)
    private val _currentUser = MutableStateFlow(
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
        )
    )
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    // Reactive Data from Repository
    val allUsers: StateFlow<List<UserProfile>> = repository.allUsers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allBatches: StateFlow<List<BatchEntity>> = repository.allBatches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSubjects: StateFlow<List<SubjectEntity>> = repository.allSubjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotices: StateFlow<List<NoticeEntity>> = repository.allNotices
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allMaterials: StateFlow<List<StudyMaterialEntity>> = repository.allMaterials
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allExams: StateFlow<List<OnlineExamEntity>> = repository.allExams
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allResults: StateFlow<List<ExamResultEntity>> = repository.allResults
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allAttendance: StateFlow<List<AttendanceEntity>> = repository.allAttendance
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allFees: StateFlow<List<FeeEntity>> = repository.allFees
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSchedules: StateFlow<List<ScheduleEntity>> = repository.allSchedules
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTransport: StateFlow<List<TransportEntity>> = repository.allTransport
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allAdmissions: StateFlow<List<AdmissionInquiryEntity>> = repository.allAdmissions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allFeedback: StateFlow<List<FeedbackEntity>> = repository.allFeedback
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Online Exam Runner State
    private val _activeExam = MutableStateFlow<OnlineExamEntity?>(null)
    val activeExam: StateFlow<OnlineExamEntity?> = _activeExam.asStateFlow()

    private val _parsedQuestions = MutableStateFlow<List<Question>>(emptyList())
    val parsedQuestions: StateFlow<List<Question>> = _parsedQuestions.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap()) // QuestionId -> SelectedOptionIndex
    val selectedAnswers: StateFlow<Map<Int, Int>> = _selectedAnswers.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _examRemainingSeconds = MutableStateFlow(0)
    val examRemainingSeconds: StateFlow<Int> = _examRemainingSeconds.asStateFlow()

    private val _lastExamResult = MutableStateFlow<ExamResultEntity?>(null)
    val lastExamResult: StateFlow<ExamResultEntity?> = _lastExamResult.asStateFlow()

    // Study Material / PDF / Video Viewer Modal
    private val _selectedMaterialForView = MutableStateFlow<StudyMaterialEntity?>(null)
    val selectedMaterialForView: StateFlow<StudyMaterialEntity?> = _selectedMaterialForView.asStateFlow()

    // Fee Payment & Receipt Modal
    private val _payingFee = MutableStateFlow<FeeEntity?>(null)
    val payingFee: StateFlow<FeeEntity?> = _payingFee.asStateFlow()

    private val _viewingReceipt = MutableStateFlow<FeeEntity?>(null)
    val viewingReceipt: StateFlow<FeeEntity?> = _viewingReceipt.asStateFlow()

    // Gemini AI Student Assistant Chat State
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "welcome_ai_guru",
                text = "Namaskar! 🙏 I am your **Pragyan AI Guru** powered by Google Gemini.\n\nI am here 24/7 to solve your doubts in **Mathematics, Physics, Chemistry, Biology, Assamese, and English** for SEBA, CBSE, NEET, and JEE examinations.\n\nHow can I help you in your studies today?",
                isFromUser = false,
                subjectTag = "General"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    private val _selectedAiSubject = MutableStateFlow("All")
    val selectedAiSubject: StateFlow<String> = _selectedAiSubject.asStateFlow()

    // Toast/Snackbar Message
    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    init {
        // Ensure initial seed if DB is opened
        viewModelScope.launch(Dispatchers.IO) {
            val users = database.coachingDao()
            // DB callback runs automatically on create
        }
    }

    // Language & Theme Actions
    fun toggleLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == Language.ENGLISH) Language.ASSAMESE else Language.ENGLISH
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun switchUserRole(role: UserRole) {
        val all = allUsers.value
        val targetUser = all.firstOrNull { it.role == role } ?: when (role) {
            UserRole.ADMIN -> UserProfile("admin_1", "Admin / Director (G. K. Baruah)", "director@pragyanacademy.com", "+91 98640 00001", UserRole.ADMIN, "All", "Administration", "ADM-001", "", 0xFFDC2626)
            UserRole.TEACHER -> UserProfile("teacher_1", "Dr. Bhaskar Sarma", "dr.sarma@pragyan.edu", "+91 98641 99887", UserRole.TEACHER, "batch_10_lakshya", "Class 10 & 12", "FAC-01", "", 0xFF059669)
            UserRole.STUDENT -> UserProfile("student_1", "Abhinav Bezbaruah", "abhinav.b@gmail.com", "+91 98640 12345", UserRole.STUDENT, "batch_10_lakshya", "Class 10 - Lakshya Batch", "PR-1024", "+91 94350 98765", 0xFF2563EB)
            UserRole.PARENT -> UserProfile("parent_1", "Dipak Bezbaruah (Parent)", "dipak.bezbaruah@gmail.com", "+91 94350 98765", UserRole.PARENT, "batch_10_lakshya", "Class 10 - Lakshya Batch", "PR-1024", "+91 94350 98765", 0xFFD97706)
            UserRole.GUEST -> UserProfile("guest_1", "New Guest / Admission Seeker", "visitor@gmail.com", "+91 90000 00000", UserRole.GUEST, "", "Prospective Student", "GUEST", "", 0xFF6B7280)
        }
        _currentUser.value = targetUser
        // Default screen per role
        when (role) {
            UserRole.ADMIN -> _currentScreen.value = AppScreen.ADMIN_PANEL
            UserRole.TEACHER -> _currentScreen.value = AppScreen.TEACHER_PANEL
            UserRole.PARENT -> _currentScreen.value = AppScreen.PARENT_PANEL
            UserRole.STUDENT -> _currentScreen.value = AppScreen.HOME
            UserRole.GUEST -> _currentScreen.value = AppScreen.ADMISSION_CONTACT
        }
        showSnackbar("Switched to ${role.name} Mode (${targetUser.name})")
    }

    fun showSnackbar(message: String) {
        _snackbarMessage.value = message
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }

    // Material Viewer
    fun openMaterial(material: StudyMaterialEntity) {
        _selectedMaterialForView.value = material
    }

    fun closeMaterial() {
        _selectedMaterialForView.value = null
    }

    fun toggleDownloadMaterial(material: StudyMaterialEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = material.copy(isDownloaded = !material.isDownloaded)
            repository.updateMaterial(updated)
            showSnackbar(if (updated.isDownloaded) "Material downloaded for offline reading!" else "Removed from offline downloads.")
        }
    }

    // Online Exam Runner
    fun startExam(exam: OnlineExamEntity) {
        _activeExam.value = exam
        _currentQuestionIndex.value = 0
        _selectedAnswers.value = emptyMap()
        _lastExamResult.value = null

        val questions = parseQuestionsJson(exam.questionsJson)
        _parsedQuestions.value = questions
        _examRemainingSeconds.value = exam.durationMinutes * 60

        _currentScreen.value = AppScreen.EXAM_RUNNER

        // Countdown timer
        viewModelScope.launch {
            while (_examRemainingSeconds.value > 0 && _currentScreen.value == AppScreen.EXAM_RUNNER) {
                delay(1000)
                _examRemainingSeconds.value -= 1
            }
            if (_examRemainingSeconds.value <= 0 && _currentScreen.value == AppScreen.EXAM_RUNNER) {
                submitExam()
            }
        }
    }

    fun selectAnswer(questionId: Int, optionIndex: Int) {
        val updated = _selectedAnswers.value.toMutableMap()
        updated[questionId] = optionIndex
        _selectedAnswers.value = updated
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < _parsedQuestions.value.size - 1) {
            _currentQuestionIndex.value += 1
        }
    }

    fun prevQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
        }
    }

    fun jumpToQuestion(index: Int) {
        if (index in 0 until _parsedQuestions.value.size) {
            _currentQuestionIndex.value = index
        }
    }

    fun submitExam() {
        val exam = _activeExam.value ?: return
        val questions = _parsedQuestions.value
        val userAnswers = _selectedAnswers.value

        var correctCount = 0
        questions.forEach { q ->
            val userAns = userAnswers[q.id]
            if (userAns != null && userAns == q.correctIndex) {
                correctCount += 1
            }
        }

        val marksPerQuestion = if (questions.isNotEmpty()) exam.totalMarks / questions.size else 1
        val score = correctCount * marksPerQuestion
        val percentage = if (exam.totalMarks > 0) (score.toFloat() / exam.totalMarks.toFloat()) * 100f else 0f
        val grade = when {
            percentage >= 90 -> "A+ (Excellent)"
            percentage >= 75 -> "A (Very Good)"
            percentage >= 60 -> "B (Good)"
            percentage >= 40 -> "C (Satisfactory)"
            else -> "Needs Improvement"
        }

        val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val submittedAt = dateFormat.format(Date())

        val resultEntity = ExamResultEntity(
            examId = exam.id,
            examTitle = exam.title,
            studentId = currentUser.value.id,
            studentName = currentUser.value.name,
            rollNo = currentUser.value.rollNo.ifEmpty { "PR-1024" },
            batchId = currentUser.value.batchId.ifEmpty { "batch_10_lakshya" },
            score = score,
            totalMarks = exam.totalMarks,
            percentage = percentage,
            grade = grade,
            submittedAt = submittedAt
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.submitExamResult(resultEntity)
            _lastExamResult.value = resultEntity
        }
    }

    fun exitExam() {
        _activeExam.value = null
        _parsedQuestions.value = emptyList()
        _selectedAnswers.value = emptyMap()
        _lastExamResult.value = null
        _currentScreen.value = AppScreen.ONLINE_EXAMS
    }

    private fun parseQuestionsJson(json: String): List<Question> {
        val list = mutableListOf<Question>()
        try {
            val array = JSONArray(json)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val id = obj.optInt("id", i + 1)
                val questionText = obj.optString("questionText", "")
                val questionTextAs = obj.optString("questionTextAs", "")
                val optionsArray = obj.getJSONArray("options")
                val options = mutableListOf<String>()
                for (j in 0 until optionsArray.length()) {
                    options.add(optionsArray.getString(j))
                }
                val correctIndex = obj.optInt("correctIndex", 0)
                val explanation = obj.optString("explanation", "")
                list.add(Question(id, questionText, questionTextAs, options, correctIndex, explanation))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    // Fee Payment Flow
    fun startFeePayment(fee: FeeEntity) {
        _payingFee.value = fee
    }

    fun closeFeePayment() {
        _payingFee.value = null
    }

    fun completeUpiPayment(transactionId: String) {
        val fee = _payingFee.value ?: return
        val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val receiptNo = "PRG-${System.currentTimeMillis() % 1000000}"

        val updatedFee = fee.copy(
            status = "PAID",
            paymentDate = currentDate,
            transactionId = transactionId,
            receiptNo = receiptNo
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFee(updatedFee)
            _payingFee.value = null
            _viewingReceipt.value = updatedFee
            showSnackbar("Fee Payment Successful! Digital receipt generated.")
        }
    }

    fun viewReceipt(fee: FeeEntity) {
        _viewingReceipt.value = fee
    }

    fun closeReceipt() {
        _viewingReceipt.value = null
    }

    // Admin / Teacher Actions
    fun addNotice(title: String, titleAs: String, description: String, descriptionAs: String, category: String, batchId: String, isImportant: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val notice = NoticeEntity(
                title = title,
                titleAs = titleAs.ifEmpty { title },
                description = description,
                descriptionAs = descriptionAs.ifEmpty { description },
                category = category,
                date = dateFormat.format(Date()),
                batchId = batchId,
                isImportant = isImportant
            )
            repository.insertNotice(notice)
            showSnackbar("Notice broadcasted successfully!")
        }
    }

    fun addStudyMaterial(title: String, titleAs: String, subject: String, classLevel: String, type: String, url: String, summary: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val material = StudyMaterialEntity(
                title = title,
                titleAs = titleAs.ifEmpty { title },
                subject = subject,
                classLevel = classLevel,
                type = type,
                urlOrContent = url,
                summary = summary,
                uploadDate = dateFormat.format(Date()),
                authorTeacher = currentUser.value.name,
                fileSize = "2.8 MB"
            )
            repository.insertMaterial(material)
            showSnackbar("Study Material uploaded successfully!")
        }
    }

    fun recordBulkAttendance(date: String, batchId: String, records: List<Pair<UserProfile, String>>) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = records.map { (user, status) ->
                AttendanceEntity(
                    date = date,
                    studentId = user.id,
                    studentName = user.name,
                    rollNo = user.rollNo,
                    batchId = batchId,
                    status = status,
                    markedBy = currentUser.value.name
                )
            }
            repository.insertAttendanceList(list)
            showSnackbar("Attendance marked for ${records.size} students!")
        }
    }

    fun createBatch(name: String, classLevel: String, roomNo: String, timing: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = "batch_${System.currentTimeMillis() % 10000}"
            val batch = BatchEntity(id, name, classLevel, roomNo, timing, 0)
            repository.insertBatch(batch)
            showSnackbar("New batch '$name' created!")
        }
    }

    fun addStudent(name: String, email: String, phone: String, batchId: String, batchName: String, rollNo: String, parentPhone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = "student_${System.currentTimeMillis() % 10000}"
            val user = UserProfile(id, name, email, phone, UserRole.STUDENT, batchId, batchName, rollNo, parentPhone, 0xFF2563EB)
            repository.insertUser(user)
            showSnackbar("Student $name registered successfully!")
        }
    }

    fun submitAdmissionInquiry(studentName: String, parentName: String, phone: String, email: String, targetClass: String, course: String, previousSchool: String, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val inquiry = AdmissionInquiryEntity(
                studentName = studentName,
                parentName = parentName,
                phone = phone,
                email = email,
                targetClass = targetClass,
                selectedCourse = course,
                previousSchool = previousSchool,
                address = address,
                status = "NEW",
                date = dateFormat.format(Date())
            )
            repository.insertAdmission(inquiry)
            showSnackbar("Admission Application submitted! Our coaching office will contact you.")
        }
    }

    fun submitFeedback(name: String, role: String, rating: Int, category: String, comment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val feedback = FeedbackEntity(
                senderName = name,
                role = role,
                rating = rating,
                category = category,
                comment = comment,
                date = dateFormat.format(Date())
            )
            repository.insertFeedback(feedback)
            showSnackbar("Thank you for your valuable feedback!")
        }
    }

    // Gemini AI Student Assistant Actions
    fun setAiSubject(subject: String) {
        _selectedAiSubject.value = subject
    }

    fun clearAiChat() {
        _chatMessages.value = listOf(
            ChatMessage(
                id = UUID.randomUUID().toString(),
                text = if (_currentLanguage.value == Language.ASSAMESE) {
                    "নমস্কাৰ! 🙏 মই আপোনাৰ প্ৰজ্ঞান এআই গুৰু। নতুন বিষয় বা প্ৰশ্নৰ সৈতে আৰম্ভ কৰক!"
                } else {
                    "Namaskar! 🙏 I am your Pragyan AI Guru. Feel free to ask any academic question or formula doubt!"
                },
                isFromUser = false,
                subjectTag = _selectedAiSubject.value
            )
        )
    }

    fun sendAiMessage(promptText: String, subject: String = _selectedAiSubject.value) {
        val trimmed = promptText.trim()
        if (trimmed.isBlank() || _isAiThinking.value) return

        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            text = trimmed,
            isFromUser = true,
            subjectTag = subject
        )

        val thinkingPlaceholderId = UUID.randomUUID().toString()
        val thinkingMessage = ChatMessage(
            id = thinkingPlaceholderId,
            text = "",
            isFromUser = false,
            isThinking = true,
            subjectTag = subject
        )

        // Build history for Gemini
        val historyList = _chatMessages.value
            .filter { !it.isThinking && !it.isError }
            .map { Pair(it.text, it.isFromUser) }

        _chatMessages.value = _chatMessages.value + userMessage + thinkingMessage
        _isAiThinking.value = true

        viewModelScope.launch {
            val result = GeminiApiClient.askGemini(
                userPrompt = trimmed,
                subjectContext = subject,
                conversationHistory = historyList
            )

            _isAiThinking.value = false

            result.onSuccess { responseText ->
                _chatMessages.value = _chatMessages.value.filter { it.id != thinkingPlaceholderId } + ChatMessage(
                    id = UUID.randomUUID().toString(),
                    text = responseText,
                    isFromUser = false,
                    subjectTag = subject
                )
            }.onFailure { error ->
                _chatMessages.value = _chatMessages.value.filter { it.id != thinkingPlaceholderId } + ChatMessage(
                    id = UUID.randomUUID().toString(),
                    text = "⚠️ Couldn't generate response. Error: ${error.message ?: "Unknown error"}. Please check your connection or try again.",
                    isFromUser = false,
                    isError = true,
                    subjectTag = subject
                )
            }
        }
    }
}

