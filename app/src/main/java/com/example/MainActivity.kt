package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.localization.getStrings
import com.example.data.model.UserRole
import com.example.ui.screens.AdmissionContactScreen
import com.example.ui.screens.AiAssistantScreen
import com.example.ui.screens.CoachingBottomNavBar
import com.example.ui.screens.CoachingTopAppBar
import com.example.ui.screens.ExamRunnerScreen
import com.example.ui.screens.FeesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NotificationListDialog
import com.example.ui.screens.OnlineExamsScreen
import com.example.ui.screens.RoleSwitcherDialog
import com.example.ui.screens.ScheduleTransportScreen
import com.example.ui.screens.StudentParentScreen
import com.example.ui.screens.StudyMaterialsScreen
import com.example.ui.screens.TeacherAdminScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CoachingViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CoachingViewModel = viewModel()
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()

            MyApplicationTheme(darkTheme = isDarkTheme) {
                CoachingApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CoachingApp(viewModel: CoachingViewModel) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    val allUsers by viewModel.allUsers.collectAsState()
    val allBatches by viewModel.allBatches.collectAsState()
    val allNotices by viewModel.allNotices.collectAsState()
    val allMaterials by viewModel.allMaterials.collectAsState()
    val allExams by viewModel.allExams.collectAsState()
    val allResults by viewModel.allResults.collectAsState()
    val allAttendance by viewModel.allAttendance.collectAsState()
    val allFees by viewModel.allFees.collectAsState()
    val allSchedules by viewModel.allSchedules.collectAsState()
    val allTransport by viewModel.allTransport.collectAsState()
    val allAdmissions by viewModel.allAdmissions.collectAsState()

    val activeExam by viewModel.activeExam.collectAsState()
    val parsedQuestions by viewModel.parsedQuestions.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val examRemainingSeconds by viewModel.examRemainingSeconds.collectAsState()
    val lastExamResult by viewModel.lastExamResult.collectAsState()

    val selectedMaterialForView by viewModel.selectedMaterialForView.collectAsState()
    val payingFee by viewModel.payingFee.collectAsState()
    val viewingReceipt by viewModel.viewingReceipt.collectAsState()
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()

    val chatMessages by viewModel.chatMessages.collectAsState()
    val isAiThinking by viewModel.isAiThinking.collectAsState()
    val selectedAiSubject by viewModel.selectedAiSubject.collectAsState()

    val strings = remember(currentLanguage) { getStrings(currentLanguage) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showRoleDialog by remember { mutableStateOf(false) }
    var showNoticesDialog by remember { mutableStateOf(false) }

    // Handle Snackbars
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    // Hardware Back Button Handling
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        if (currentScreen == AppScreen.EXAM_RUNNER) {
            viewModel.exitExam()
        } else {
            viewModel.navigateTo(AppScreen.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (currentScreen != AppScreen.EXAM_RUNNER) {
                CoachingTopAppBar(
                    strings = strings,
                    currentLanguage = currentLanguage,
                    isDarkTheme = isDarkTheme,
                    currentUser = currentUser,
                    unreadNoticesCount = allNotices.count { it.isImportant },
                    onLanguageToggle = { viewModel.toggleLanguage() },
                    onThemeToggle = { viewModel.toggleTheme() },
                    onRoleClick = { showRoleDialog = true },
                    onNotificationClick = { showNoticesDialog = true }
                )
            }
        },
        bottomBar = {
            if (currentScreen != AppScreen.EXAM_RUNNER) {
                CoachingBottomNavBar(
                    currentScreen = currentScreen,
                    currentUser = currentUser,
                    strings = strings,
                    onNavigate = { viewModel.navigateTo(it) }
                )
            }
        }
    ) { innerPadding ->
        Crossfade(
            targetState = currentScreen,
            modifier = Modifier.padding(innerPadding),
            label = "ScreenCrossfade"
        ) { screen ->
            when (screen) {
                AppScreen.HOME -> HomeScreen(
                    strings = strings,
                    currentLanguage = currentLanguage,
                    currentUser = currentUser,
                    notices = allNotices,
                    onNavigate = { viewModel.navigateTo(it) },
                    onOpenNoticeList = { showNoticesDialog = true }
                )

                AppScreen.STUDENT_PANEL -> StudentParentScreen(
                    strings = strings,
                    currentLanguage = currentLanguage,
                    currentUser = currentUser,
                    isParentView = false,
                    attendanceList = allAttendance,
                    feesList = allFees,
                    resultsList = allResults,
                    onPayFeeClick = { viewModel.startFeePayment(it) },
                    onViewReceiptClick = { viewModel.viewReceipt(it) },
                    onNavigate = { viewModel.navigateTo(it) },
                    onShowMessage = { viewModel.showSnackbar(it) }
                )

                AppScreen.PARENT_PANEL -> StudentParentScreen(
                    strings = strings,
                    currentLanguage = currentLanguage,
                    currentUser = currentUser,
                    isParentView = true,
                    attendanceList = allAttendance,
                    feesList = allFees,
                    resultsList = allResults,
                    onPayFeeClick = { viewModel.startFeePayment(it) },
                    onViewReceiptClick = { viewModel.viewReceipt(it) },
                    onNavigate = { viewModel.navigateTo(it) },
                    onShowMessage = { viewModel.showSnackbar(it) }
                )

                AppScreen.TEACHER_PANEL -> TeacherAdminScreen(
                    strings = strings,
                    currentLanguage = currentLanguage,
                    currentUser = currentUser,
                    isAdminMode = false,
                    allUsers = allUsers,
                    allBatches = allBatches,
                    allAdmissions = allAdmissions,
                    onAddNotice = { t, ta, d, da, cat, b, imp -> viewModel.addNotice(t, ta, d, da, cat, b, imp) },
                    onAddMaterial = { t, ta, s, c, type, url, sum -> viewModel.addStudyMaterial(t, ta, s, c, type, url, sum) },
                    onRecordAttendance = { date, b, recs -> viewModel.recordBulkAttendance(date, b, recs) },
                    onCreateBatch = { name, c, r, t -> viewModel.createBatch(name, c, r, t) },
                    onAddStudent = { n, e, p, b, bn, roll, pp -> viewModel.addStudent(n, e, p, b, bn, roll, pp) },
                    onUpdateAdmissionStatus = { _, _ -> viewModel.showSnackbar("Updated student admission record status.") },
                    onNavigate = { viewModel.navigateTo(it) },
                    onShowMessage = { viewModel.showSnackbar(it) }
                )

                AppScreen.ADMIN_PANEL -> TeacherAdminScreen(
                    strings = strings,
                    currentLanguage = currentLanguage,
                    currentUser = currentUser,
                    isAdminMode = true,
                    allUsers = allUsers,
                    allBatches = allBatches,
                    allAdmissions = allAdmissions,
                    onAddNotice = { t, ta, d, da, cat, b, imp -> viewModel.addNotice(t, ta, d, da, cat, b, imp) },
                    onAddMaterial = { t, ta, s, c, type, url, sum -> viewModel.addStudyMaterial(t, ta, s, c, type, url, sum) },
                    onRecordAttendance = { date, b, recs -> viewModel.recordBulkAttendance(date, b, recs) },
                    onCreateBatch = { name, c, r, t -> viewModel.createBatch(name, c, r, t) },
                    onAddStudent = { n, e, p, b, bn, roll, pp -> viewModel.addStudent(n, e, p, b, bn, roll, pp) },
                    onUpdateAdmissionStatus = { _, _ -> viewModel.showSnackbar("Updated student admission record status.") },
                    onNavigate = { viewModel.navigateTo(it) },
                    onShowMessage = { viewModel.showSnackbar(it) }
                )

                AppScreen.STUDY_MATERIALS -> StudyMaterialsScreen(
                    materials = allMaterials,
                    strings = strings,
                    currentLanguage = currentLanguage,
                    selectedMaterial = selectedMaterialForView,
                    onOpenMaterial = { viewModel.openMaterial(it) },
                    onCloseMaterial = { viewModel.closeMaterial() },
                    onToggleDownload = { viewModel.toggleDownloadMaterial(it) }
                )

                AppScreen.ONLINE_EXAMS -> OnlineExamsScreen(
                    exams = allExams,
                    results = allResults,
                    strings = strings,
                    currentLanguage = currentLanguage,
                    onStartExam = { viewModel.startExam(it) }
                )

                AppScreen.EXAM_RUNNER -> activeExam?.let { exam ->
                    ExamRunnerScreen(
                        exam = exam,
                        questions = parsedQuestions,
                        selectedAnswers = selectedAnswers,
                        currentQuestionIndex = currentQuestionIndex,
                        remainingSeconds = examRemainingSeconds,
                        lastResult = lastExamResult,
                        strings = strings,
                        currentLanguage = currentLanguage,
                        onSelectAnswer = { qId, opt -> viewModel.selectAnswer(qId, opt) },
                        onNextQuestion = { viewModel.nextQuestion() },
                        onPrevQuestion = { viewModel.prevQuestion() },
                        onJumpToQuestion = { viewModel.jumpToQuestion(it) },
                        onSubmitExam = { viewModel.submitExam() },
                        onExitExam = { viewModel.exitExam() }
                    )
                } ?: run {
                    viewModel.navigateTo(AppScreen.ONLINE_EXAMS)
                }

                AppScreen.FEES_PAYMENT -> FeesScreen(
                    fees = allFees,
                    strings = strings,
                    currentLanguage = currentLanguage,
                    payingFee = payingFee,
                    viewingReceipt = viewingReceipt,
                    onStartPayment = { viewModel.startFeePayment(it) },
                    onClosePayment = { viewModel.closeFeePayment() },
                    onCompleteUpiPayment = { viewModel.completeUpiPayment(it) },
                    onViewReceipt = { viewModel.viewReceipt(it) },
                    onCloseReceipt = { viewModel.closeReceipt() },
                    onShowMessage = { viewModel.showSnackbar(it) }
                )

                AppScreen.CLASS_SCHEDULE -> ScheduleTransportScreen(
                    schedules = allSchedules,
                    transports = allTransport,
                    strings = strings,
                    currentLanguage = currentLanguage,
                    isTransportOnly = false
                )

                AppScreen.TRANSPORT -> ScheduleTransportScreen(
                    schedules = allSchedules,
                    transports = allTransport,
                    strings = strings,
                    currentLanguage = currentLanguage,
                    isTransportOnly = true
                )

                AppScreen.ADMISSION_CONTACT -> AdmissionContactScreen(
                    strings = strings,
                    currentLanguage = currentLanguage,
                    currentUser = currentUser,
                    isGalleryOnly = false,
                    onSubmitAdmission = { sn, pn, ph, em, tc, crs, sc, ad ->
                        viewModel.submitAdmissionInquiry(sn, pn, ph, em, tc, crs, sc, ad)
                    },
                    onSubmitFeedback = { nm, r, rat, cat, com ->
                        viewModel.submitFeedback(nm, r, rat, cat, com)
                    },
                    onShowMessage = { viewModel.showSnackbar(it) }
                )

                AppScreen.GALLERY_TESTIMONIALS -> AdmissionContactScreen(
                    strings = strings,
                    currentLanguage = currentLanguage,
                    currentUser = currentUser,
                    isGalleryOnly = true,
                    onSubmitAdmission = { sn, pn, ph, em, tc, crs, sc, ad ->
                        viewModel.submitAdmissionInquiry(sn, pn, ph, em, tc, crs, sc, ad)
                    },
                    onSubmitFeedback = { nm, r, rat, cat, com ->
                        viewModel.submitFeedback(nm, r, rat, cat, com)
                    },
                    onShowMessage = { viewModel.showSnackbar(it) }
                )

                AppScreen.AI_ASSISTANT -> AiAssistantScreen(
                    chatMessages = chatMessages,
                    isAiThinking = isAiThinking,
                    selectedSubject = selectedAiSubject,
                    currentUser = currentUser,
                    strings = strings,
                    currentLanguage = currentLanguage,
                    onSendMessage = { prompt, subj -> viewModel.sendAiMessage(prompt, subj) },
                    onClearChat = { viewModel.clearAiChat() },
                    onSelectSubject = { viewModel.setAiSubject(it) },
                    onShowMessage = { viewModel.showSnackbar(it) }
                )
            }
        }
    }

    // Role Switcher Dialog
    if (showRoleDialog) {
        RoleSwitcherDialog(
            currentUser = currentUser,
            strings = strings,
            onRoleSelected = { viewModel.switchUserRole(it) },
            onDismiss = { showRoleDialog = false }
        )
    }

    // Announcements Dialog
    if (showNoticesDialog) {
        NotificationListDialog(
            notices = allNotices,
            strings = strings,
            currentLanguage = currentLanguage,
            onDismiss = { showNoticesDialog = false }
        )
    }
}
