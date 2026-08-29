package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.localization.Language
import com.example.data.localization.Strings
import com.example.data.model.StudyMaterialEntity
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CoralRose
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.NavyBluePrimary
import com.example.ui.theme.PurpleBadge
import com.example.ui.theme.RoyalBlueLight

@Composable
fun StudyMaterialsScreen(
    materials: List<StudyMaterialEntity>,
    strings: Strings,
    currentLanguage: Language,
    selectedMaterial: StudyMaterialEntity?,
    onOpenMaterial: (StudyMaterialEntity) -> Unit,
    onCloseMaterial: () -> Unit,
    onToggleDownload: (StudyMaterialEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedClassTab by remember { mutableStateOf("All") }
    var selectedTypeFilter by remember { mutableStateOf("All") }

    val classTabs = listOf("All", "Class 10", "Class 12", "Class 9", "Class 11", "NEET/JEE")
    val typeFilters = listOf(
        "All" to "All Notes",
        "PDF" to strings.pdfNotes,
        "VIDEO" to strings.videoLectures,
        "PYQ" to strings.pyqQuestions,
        "NOTES" to strings.importantQuestions
    )

    val filteredMaterials = materials.filter { mat ->
        val matchesClass = selectedClassTab == "All" || mat.classLevel.contains(selectedClassTab, ignoreCase = true)
        val matchesType = selectedTypeFilter == "All" || mat.type == selectedTypeFilter
        val matchesQuery = searchQuery.isBlank() ||
                mat.title.contains(searchQuery, ignoreCase = true) ||
                mat.titleAs.contains(searchQuery, ignoreCase = true) ||
                mat.subject.contains(searchQuery, ignoreCase = true) ||
                mat.summary.contains(searchQuery, ignoreCase = true)
        matchesClass && matchesType && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Search & Filter Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = strings.studyMaterials,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search topics, chapters, formula sheets...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("materials_search_input"),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Class Tabs
            ScrollableTabRow(
                selectedTabIndex = classTabs.indexOf(selectedClassTab).coerceAtLeast(0),
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                divider = {}
            ) {
                classTabs.forEach { cls ->
                    Tab(
                        selected = selectedClassTab == cls,
                        onClick = { selectedClassTab = cls },
                        text = {
                            Text(
                                text = cls,
                                fontSize = 12.sp,
                                fontWeight = if (selectedClassTab == cls) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Type Filter Chips (PDF, Video, PYQ, Notes)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(typeFilters) { (typeKey, label) ->
                    FilterChip(
                        selected = selectedTypeFilter == typeKey,
                        onClick = { selectedTypeFilter = typeKey },
                        label = { Text(label, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Materials List
        if (filteredMaterials.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = "Empty",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No study materials match your search filter.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredMaterials) { mat ->
                    val displayTitle = if (currentLanguage == Language.ASSAMESE && mat.titleAs.isNotEmpty()) mat.titleAs else mat.title

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenMaterial(mat) }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = when (mat.type) {
                                            "PDF" -> CoralRose
                                            "VIDEO" -> RoyalBlueLight
                                            "PYQ" -> PurpleBadge
                                            else -> EmeraldSuccess
                                        }
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Icon(
                                                imageVector = when (mat.type) {
                                                    "PDF" -> Icons.Default.PictureAsPdf
                                                    "VIDEO" -> Icons.Default.OndemandVideo
                                                    "PYQ" -> Icons.Default.Quiz
                                                    else -> Icons.Default.TextSnippet
                                                },
                                                contentDescription = mat.type,
                                                tint = Color.White,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = mat.type,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = "${mat.classLevel} • ${mat.subject}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Text(
                                    text = mat.uploadDate,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = displayTitle,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = mat.summary,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "By ${mat.authorTeacher} • ${mat.fileSize}",
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    // Offline Download Toggle
                                    IconButton(
                                        onClick = { onToggleDownload(mat) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (mat.isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                            contentDescription = "Download",
                                            tint = if (mat.isDownloaded) EmeraldSuccess else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Button(
                                        onClick = { onOpenMaterial(mat) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (mat.type == "VIDEO") RoyalBlueLight else NavyBluePrimary
                                        ),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                        modifier = Modifier.height(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (mat.type == "VIDEO") Icons.Default.PlayArrow else Icons.Default.MenuBook,
                                            contentDescription = "Open",
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (mat.type == "VIDEO") strings.playVideo else strings.openPdf,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal PDF Viewer / Video Player
    selectedMaterial?.let { material ->
        if (material.type == "VIDEO") {
            VideoPlayerDialog(
                material = material,
                currentLanguage = currentLanguage,
                onDismiss = onCloseMaterial
            )
        } else {
            PdfViewerDialog(
                material = material,
                currentLanguage = currentLanguage,
                onToggleDownload = { onToggleDownload(material) },
                onDismiss = onCloseMaterial
            )
        }
    }
}

// In-App PDF Reader Dialog with Zoom & Bookmarking
@Composable
private fun PdfViewerDialog(
    material: StudyMaterialEntity,
    currentLanguage: Language,
    onToggleDownload: () -> Unit,
    onDismiss: () -> Unit
) {
    var textScale by remember { mutableFloatStateOf(1.0f) }
    var currentPage by remember { mutableIntStateOf(1) }
    val totalPages = 4

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Reader Top Bar
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            IconButton(onClick = onDismiss) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(
                                    text = material.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${material.subject} • Page $currentPage of $totalPages",
                                    fontSize = 10.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }

                        Row {
                            IconButton(onClick = { textScale = (textScale - 0.15f).coerceAtLeast(0.8f) }) {
                                Icon(Icons.Default.ZoomOut, contentDescription = "Zoom Out", tint = Color.White)
                            }
                            IconButton(onClick = { textScale = (textScale + 0.15f).coerceAtMost(1.5f) }) {
                                Icon(Icons.Default.ZoomIn, contentDescription = "Zoom In", tint = Color.White)
                            }
                            IconButton(onClick = onToggleDownload) {
                                Icon(
                                    imageVector = if (material.isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                                    contentDescription = "Download",
                                    tint = if (material.isDownloaded) AmberGold else Color.White
                                )
                            }
                        }
                    }
                }

                // Simulated Document Page
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "PRAGYAN COACHING CENTRE • MASTER STUDY NOTES",
                                    fontSize = (10 * textScale).sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = material.title,
                                    fontSize = (15 * textScale).sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        // Formula & Core Concepts
                        Text(
                            text = "1. Key Concepts & Chapter Outline:",
                            fontSize = (13 * textScale).sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "• ${material.summary}\n• Balanced Chemical Equations: Law of conservation of mass holds true for all chemical processes.\n• Exothermic vs Endothermic reactions: Heat released vs Heat absorbed.\n• Redox reactions: Oxidation is gain of oxygen / loss of electrons. Reduction is gain of hydrogen / gain of electrons.",
                            fontSize = (12 * textScale).sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = (18 * textScale).sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Assamese Explanations Box
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AmberGold.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "অসমীয়া মাধ্যমৰ বিশেষ টোকা (Key Takeaways in Assamese):",
                                    fontSize = (12 * textScale).sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "১. ৰাসায়নিক বিক্ৰিয়াৰ সময়ত মৌলৰ পৰমাণুসমূহৰ মাজত বান্ধনি ভাঙি নতুন বান্ধনি গঠিত হয়।\n২. সংমিশ্ৰণ বিক্ৰিয়া: দুটা বা ততোধিক পদাৰ্থ লগ হৈ এটা মাত্ৰ নতুন পদাৰ্থ সৃষ্টি কৰে ( যেনে: C + O2 -> CO2 )।\n৩. তাপবৰ্জী বিক্ৰিয়া: বিক্ৰিয়াত তাপশক্তি উৎপন্ন হয় (যেনে: শ্বাস-প্ৰশ্বাস আৰু চূণত পানী যোগ কৰা)।",
                                    fontSize = (11 * textScale).sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = (17 * textScale).sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Sample Question & Solution
                        Text(
                            text = "2. Previous Board & Most Expected Questions (Solved):",
                            fontSize = (13 * textScale).sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Q1. Why should a magnesium ribbon be cleaned before burning in air? (HSLC 2023, CBSE 2024)\nAnswer: Magnesium reacts with oxygen in the air to form a protective layer of magnesium oxide (MgO) on its surface. Cleaning with sandpaper removes this oxide layer, allowing it to burn efficiently in oxygen with a dazzling white flame.\n\nQ2. Write balanced equations for iron with steam.\nAnswer: 3Fe(s) + 4H2O(g) -> Fe3O4(s) + 4H2(g)",
                            fontSize = (11 * textScale).sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = (16 * textScale).sp
                        )
                    }
                }

                // Page Navigation Footer
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { if (currentPage > 1) currentPage -= 1 },
                            enabled = currentPage > 1,
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("Prev Page", fontSize = 11.sp)
                        }

                        Text(
                            text = "Page $currentPage / $totalPages",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = { if (currentPage < totalPages) currentPage += 1 },
                            enabled = currentPage < totalPages,
                            colors = ButtonDefaults.buttonColors(containerColor = NavyBluePrimary),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("Next Page", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

// In-App Video Player Dialog
@Composable
private fun VideoPlayerDialog(
    material: StudyMaterialEntity,
    currentLanguage: Language,
    onDismiss: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(true) }
    var currentProgress by remember { mutableFloatStateOf(0.35f) }
    var playbackSpeed by remember { mutableStateOf("1.0x") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = material.title,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                // Simulated 16:9 Video Canvas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .background(Color(0xFF0F172A)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = CircleShape,
                            color = RoyalBlueLight.copy(alpha = 0.9f),
                            modifier = Modifier
                                .size(56.dp)
                                .clickable { isPlaying = !isPlaying }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isPlaying) "Playing Lecture • 15:42 / 45:00" else "Paused",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                    }

                    // Progress Bar & Controls at bottom of video
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Slider(
                            value = currentProgress,
                            onValueChange = { currentProgress = it },
                            modifier = Modifier.height(20.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("15:42", color = Color.White, fontSize = 10.sp)
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color.White.copy(alpha = 0.2f),
                                modifier = Modifier.clickable {
                                    playbackSpeed = when (playbackSpeed) {
                                        "1.0x" -> "1.25x"
                                        "1.25x" -> "1.5x"
                                        "1.5x" -> "2.0x"
                                        else -> "1.0x"
                                    }
                                }
                            ) {
                                Text(
                                    text = "Speed: $playbackSpeed",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text("45:00", color = Color.White, fontSize = 10.sp)
                        }
                    }
                }

                // Video Details & Syllabus Timestamps
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    item {
                        Text(
                            text = material.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Faculty: ${material.authorTeacher} • ${material.classLevel} ${material.subject}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = material.summary,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Lecture Chapters & Timestamps:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        val timestamps = listOf(
                            "00:00" to "Introduction & Sign Convention for Lenses",
                            "10:15" to "Derivation of Lens Formula: 1/f = 1/v - 1/u",
                            "24:30" to "Magnification & Power of Lens (Numerical Solving)",
                            "36:45" to "High-Probability Board Questions Discussion"
                        )

                        timestamps.forEach { (time, title) ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = RoyalBlueLight
                                    ) {
                                        Text(
                                            text = time,
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = title,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
