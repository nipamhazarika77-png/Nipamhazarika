package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.Language
import com.example.data.localization.Strings
import com.example.data.model.AiPromptTopic
import com.example.data.model.ChatMessage
import com.example.data.model.UserProfile
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val IndigoPrimary = Color(0xFF4F46E5)
private val PurpleAccent = Color(0xFF7C3AED)
private val AmberAccent = Color(0xFFF59E0B)
private val EmeraldAccent = Color(0xFF10B981)

@Composable
fun AiAssistantScreen(
    chatMessages: List<ChatMessage>,
    isAiThinking: Boolean,
    selectedSubject: String,
    currentUser: UserProfile,
    strings: Strings,
    currentLanguage: Language,
    onSendMessage: (String, String) -> Unit,
    onClearChat: () -> Unit,
    onSelectSubject: (String) -> Unit,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val subjects = listOf(
        "All" to (if (currentLanguage == Language.ASSAMESE) "সকলো বিষয়" else "All Subjects"),
        "Mathematics" to (if (currentLanguage == Language.ASSAMESE) "গণিত (Maths)" else "Mathematics"),
        "Physics" to (if (currentLanguage == Language.ASSAMESE) "পদাৰ্থ বিজ্ঞান" else "Physics"),
        "Chemistry" to (if (currentLanguage == Language.ASSAMESE) "ৰসায়ন বিজ্ঞান" else "Chemistry"),
        "Biology" to (if (currentLanguage == Language.ASSAMESE) "জীৱবিজ্ঞান (NEET)" else "Biology / NEET"),
        "Assamese" to (if (currentLanguage == Language.ASSAMESE) "অসমীয়া ব্যাকৰণ" else "Assamese Grammar"),
        "JEE / NEET" to (if (currentLanguage == Language.ASSAMESE) "JEE / NEET টিপ্স" else "JEE / NEET Tips"),
        "Study Plan" to (if (currentLanguage == Language.ASSAMESE) "পঢ়াৰ সময়সূচী" else "Study Planner")
    )

    val quickPrompts = remember(currentLanguage) {
        listOf(
            AiPromptTopic(
                id = "trig",
                title = "Trigonometric Identities",
                titleAs = "ত্ৰিকোণমিতিৰ সূত্ৰসমূহ",
                prompt = "Explain all fundamental Trigonometric Identities for Class 10/11 with proofs and memory tips.",
                promptAs = "দশম আৰু একাদশ শ্ৰেণীৰ ত্ৰিকোণমিতিৰ মূল সূত্ৰসমূহ বুজাই দিয়ক।",
                subject = "Mathematics",
                iconName = "math"
            ),
            AiPromptTopic(
                id = "newton",
                title = "Newton's Laws of Motion",
                titleAs = "নিউটনৰ গতিৰ সূত্ৰসমূহ",
                prompt = "Explain Newton's Three Laws of Motion with real-world examples and mathematical formulations.",
                promptAs = "নিউটনৰ তিনিটা গতিৰ সূত্ৰ উদাহৰণ সহ বুজাই দিয়ক।",
                subject = "Physics",
                iconName = "physics"
            ),
            AiPromptTopic(
                id = "optics",
                title = "Lens & Mirror Formulas",
                titleAs = "দাপোণ আৰু লেন্সৰ সূত্ৰ",
                prompt = "Break down Lens & Mirror formulas with Cartesian Sign Conventions for Class 10/12 Physics.",
                promptAs = "দাপোণ আৰু লেন্সৰ ফৰ্মুলা আৰু চিহ্ন প্ৰণালী বিশদভাৱে বুজাই দিয়ক।",
                subject = "Physics",
                iconName = "physics"
            ),
            AiPromptTopic(
                id = "assamese_grammar",
                title = "Assamese কাৰক আৰু বিভক্তি",
                titleAs = "কাৰক আৰু বিভক্তি",
                prompt = "Explain the 6 types of কাৰক (Karok) in Assamese Grammar with examples and Vibhakti rules.",
                promptAs = "অসমীয়া ব্যাকৰণৰ ছয় প্ৰকাৰৰ কাৰক আৰু বিভক্তিৰ নিয়মসমূহ উদাহৰণসহ বুজাই দিয়ক।",
                subject = "Assamese",
                iconName = "language"
            ),
            AiPromptTopic(
                id = "neet_bio",
                title = "NEET Biology High-Yield",
                titleAs = "NEET জীৱবিজ্ঞান গুৰুত্বপূৰ্ণ বিষয়",
                prompt = "What are the most high-yield NCERT Biology chapters and revision strategy for NEET aspirants?",
                promptAs = "NEET পৰীক্ষাৰ বাবে জীৱবিজ্ঞানৰ কোনবোৰ অধ্যায় আটাইতকৈ গুৰুত্বপূৰ্ণ?",
                subject = "Biology",
                iconName = "biology"
            ),
            AiPromptTopic(
                id = "study_routine",
                title = "30-Day Exam Timetable",
                titleAs = "৩০ দিনীয়া অধ্যয়ন পৰিকল্পনা",
                prompt = "Create a structured 30-day revision timetable for Board Exam and competitive entrance preparation.",
                promptAs = "পৰীক্ষাৰ বাবে ৩০ দিনীয়া এখন ফলপ্ৰসূ ৰুটিন আৰু পৰিকল্পনা প্ৰস্তুত কৰি দিয়ক।",
                subject = "Study Plan",
                iconName = "planner"
            )
        )
    }

    // Auto-scroll to bottom on new messages
    LaunchedEffect(chatMessages.size, isAiThinking) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Hero Header Banner
        AiHeroBanner(
            selectedSubject = selectedSubject,
            strings = strings,
            currentLanguage = currentLanguage,
            onClearChat = onClearChat
        )

        // Subject Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(subjects) { (key, label) ->
                val isSelected = selectedSubject == key
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectSubject(key) },
                    label = {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    leadingIcon = {
                        val icon = when (key) {
                            "Mathematics" -> Icons.Default.Calculate
                            "Physics" -> Icons.Default.Science
                            "Chemistry" -> Icons.Default.Science
                            "Biology" -> Icons.Default.Psychology
                            "Assamese" -> Icons.Default.Translate
                            "JEE / NEET" -> Icons.Default.AutoAwesome
                            "Study Plan" -> Icons.Default.MenuBook
                            else -> Icons.Default.SmartToy
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFEEF2FF),
                        selectedLabelColor = IndigoPrimary
                    ),
                    modifier = Modifier.testTag("filter_subject_$key")
                )
            }
        }

        // Quick Suggestion Cards if chat has few messages
        AnimatedVisibility(
            visible = chatMessages.size <= 2,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Text(
                    text = strings.aiSuggestedPrompts,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(quickPrompts) { topic ->
                        Card(
                            onClick = {
                                val prompt = if (currentLanguage == Language.ASSAMESE) topic.promptAs else topic.prompt
                                onSendMessage(prompt, topic.subject)
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .widthIn(max = 220.dp)
                                .testTag("quick_prompt_${topic.id}")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(IndigoPrimary.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = AmberAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = if (currentLanguage == Language.ASSAMESE) topic.titleAs else topic.title,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = topic.subject,
                                        fontSize = 10.sp,
                                        color = IndigoPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(chatMessages, key = { it.id }) { message ->
                ChatMessageItem(
                    message = message,
                    currentLanguage = currentLanguage,
                    onCopyText = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Pragyan AI Answer", message.text)
                        clipboard.setPrimaryClip(clip)
                        onShowMessage("Answer copied to clipboard!")
                    }
                )
            }
        }

        // Bottom Input and Action Panel
        Surface(
            tonalElevation = 6.dp,
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = {
                            Text(
                                text = strings.askAnythingPrompt,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        },
                        maxLines = 4,
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IndigoPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("ai_prompt_input")
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank() && !isAiThinking) {
                                val prompt = inputText
                                inputText = ""
                                onSendMessage(prompt, selectedSubject)
                            }
                        },
                        enabled = inputText.isNotBlank() && !isAiThinking,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (inputText.isNotBlank() && !isAiThinking) {
                                    Brush.linearGradient(listOf(IndigoPrimary, PurpleAccent))
                                } else {
                                    Brush.linearGradient(listOf(Color(0xFFCBD5E1), Color(0xFF94A3B8)))
                                }
                            )
                            .testTag("send_ai_prompt_button")
                    ) {
                        if (isAiThinking) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Disclaimer
                Text(
                    text = strings.aiDisclaimer,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun AiHeroBanner(
    selectedSubject: String,
    strings: Strings,
    currentLanguage: Language,
    onClearChat: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF312E81), // Deep Indigo
                            Color(0xFF4F46E5), // Indigo 600
                            Color(0xFF7C3AED)  // Purple 600
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFFFDE047), // Gold Star
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = strings.geminiAssistant,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = EmeraldAccent.copy(alpha = 0.25f)
                            ) {
                                Text(
                                    text = "3.5 Flash",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFA7F3D0),
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = strings.aiStudentHelpTagline,
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }

                IconButton(
                    onClick = onClearChat,
                    modifier = Modifier.testTag("clear_chat_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Clear Chat",
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessageItem(
    message: ChatMessage,
    currentLanguage: Language,
    onCopyText: () -> Unit
) {
    val isUser = message.isFromUser
    val timeFormat = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val timeString = remember(message.timestamp) { timeFormat.format(Date(message.timestamp)) }
    var copied by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(IndigoPrimary, PurpleAccent))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI Guru",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 16.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 16.dp
                ),
                color = if (isUser) {
                    IndigoPrimary
                } else if (message.isError) {
                    MaterialTheme.colorScheme.errorContainer
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                shadowElevation = if (isUser) 2.dp else 1.dp,
                modifier = Modifier.testTag(if (isUser) "user_message_bubble" else "ai_message_bubble")
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    if (message.isThinking) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(16.dp),
                                color = IndigoPrimary
                            )
                            Text(
                                text = if (currentLanguage == Language.ASSAMESE) "গুৰুৱে ব্যাখ্যা প্ৰস্তুত কৰি আছে..." else "Thinking & solving step-by-step...",
                                fontSize = 12.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        SelectionContainer {
                            FormattedMessageText(
                                text = message.text,
                                isUser = isUser,
                                isError = message.isError
                            )
                        }
                    }
                }
            }

            // Message footer (timestamp + copy button)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp)
            ) {
                Text(
                    text = timeString,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )

                if (!isUser && !message.isThinking && !message.isError) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable {
                                copied = true
                                onCopyText()
                            }
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = if (copied) Icons.Default.Check else Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = if (copied) EmeraldAccent else IndigoPrimary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = if (copied) "Copied" else "Copy",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (copied) EmeraldAccent else IndigoPrimary
                        )
                    }
                }
            }
        }

        if (isUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E7FF)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = IndigoPrimary
                )
            }
        }
    }
}

@Composable
private fun FormattedMessageText(
    text: String,
    isUser: Boolean,
    isError: Boolean
) {
    val textColor = if (isUser) {
        Color.White
    } else if (isError) {
        MaterialTheme.colorScheme.onErrorContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    // Render cleanly with markdown bold and header highlights
    val lines = text.split("\n")
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        lines.forEach { rawLine ->
            val line = rawLine.trimEnd()
            when {
                line.startsWith("### ") -> {
                    Text(
                        text = line.removePrefix("### ").trim(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isUser) Color.White else IndigoPrimary,
                        modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                    )
                }
                line.startsWith("## ") -> {
                    Text(
                        text = line.removePrefix("## ").trim(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isUser) Color.White else PurpleAccent,
                        modifier = Modifier.padding(top = 6.dp, bottom = 2.dp)
                    )
                }
                line.startsWith("- ") || line.startsWith("* ") -> {
                    Row(modifier = Modifier.padding(start = 4.dp)) {
                        Text(
                            text = "• ",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isUser) Color.White else IndigoPrimary
                        )
                        Text(
                            text = stripMarkdown(line.substring(2)),
                            fontSize = 13.sp,
                            color = textColor,
                            lineHeight = 18.sp
                        )
                    }
                }
                line.startsWith("$$") && line.endsWith("$$") -> {
                    Surface(
                        color = if (isUser) Color.White.copy(alpha = 0.15f) else Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    ) {
                        Text(
                            text = line.replace("$$", "").trim(),
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = if (isUser) Color.White else IndigoPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
                line.isNotBlank() -> {
                    Text(
                        text = stripMarkdown(line),
                        fontSize = 13.sp,
                        color = textColor,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

private fun stripMarkdown(input: String): String {
    return input
        .replace("**", "")
        .replace("__", "")
        .replace("`", "")
        .replace("$", "")
}
