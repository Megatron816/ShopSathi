package com.example.shoppinglist2app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist2app.ui.components.*
import com.example.shoppinglist2app.ui.theme.*
import com.example.shoppinglist2app.viewmodel.ShoppingViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(
    viewModel: ShoppingViewModel,
    onDateSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    val eventDates    by viewModel.allEventDates.collectAsState()
    val eventDatesSet  = remember(eventDates) { eventDates.toSet() }
    var currentMonth  by remember { mutableStateOf(YearMonth.now()) }
    val today          = LocalDate.now().toString()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📅  Calendar", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = SkyBlueDark)
            }
        }

        // ── Calendar card ────────────────────────────────────────────────────
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                // Month navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(SkyBlueLight)
                        .clickable { currentMonth = currentMonth.minusMonths(1) },
                        contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.ChevronLeft, null, tint = SkyBlueDark, modifier = Modifier.size(20.dp))
                    }
                    Text(
                        text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                        fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SkyBlueDark
                    )
                    Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(SkyBlueLight)
                        .clickable { currentMonth = currentMonth.plusMonths(1) },
                        contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.ChevronRight, null, tint = SkyBlueDark, modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Day headers
                val dayNames = listOf("Sun","Mon","Tue","Wed","Thu","Fri","Sat")
                Row(Modifier.fillMaxWidth()) {
                    dayNames.forEach { day ->
                        Text(day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center,
                            fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SkyBlueMedium)
                    }
                }
                Spacer(Modifier.height(8.dp))

                // Grid
                val firstDay    = currentMonth.atDay(1)
                val startOffset = firstDay.dayOfWeek.value % 7
                val daysInMonth = currentMonth.lengthOfMonth()
                val rows        = (startOffset + daysInMonth + 6) / 7

                repeat(rows) { row ->
                    Row(Modifier.fillMaxWidth()) {
                        repeat(7) { col ->
                            val dayNum = row * 7 + col - startOffset + 1
                            if (dayNum in 1..daysInMonth) {
                                val dateStr  = currentMonth.atDay(dayNum).toString()
                                val isToday  = dateStr == today
                                val hasEvent = dateStr in eventDatesSet
                                val isPast   = viewModel.isPast(dateStr)
                                val isFuture = viewModel.isFuture(dateStr)
                                DayCell(
                                    day      = dayNum, isToday = isToday, hasEvent = hasEvent,
                                    isPast   = isPast, isFuture = isFuture,
                                    modifier = Modifier.weight(1f),
                                    onClick  = { viewModel.selectDate(dateStr); onDateSelected(dateStr) }
                                )
                            } else {
                                Box(Modifier.weight(1f))
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                }
            }
        }

        // ── Legend ───────────────────────────────────────────────────────────
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                LegendDot(Color(0xFF10B981), "Has items")
                LegendDot(Color(0xFFFBBF24), "Future event")
                LegendDot(SkyBluePrimary, "Today")
            }
        }

        // ── Upcoming events ───────────────────────────────────────────────────
        val futureEvents = eventDates.filter { viewModel.isFuture(it) }.take(3)
        if (futureEvents.isNotEmpty()) {
            item { SectionHeader("Upcoming Events") }
            items(futureEvents.size) { idx ->
                val date = futureEvents[idx]
                val fmt  = try { LocalDate.parse(date).format(DateTimeFormatter.ofPattern("EEEE, MMM d yyyy")) } catch (e: Exception) { date }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .shadow(1.dp, RoundedCornerShape(14.dp))
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .border(1.dp, SkyBlueBorder, RoundedCornerShape(14.dp))
                        .clickable { viewModel.selectDate(date); onDateSelected(date) }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column {
                        Text(fmt, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SkyBlueDark)
                        Text("Tap to view/edit", fontSize = 11.sp, color = SkyBlueMedium)
                    }
                    Box(modifier = Modifier.clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFEF9C3)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                        Text("Future", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCell(
    day: Int, isToday: Boolean, hasEvent: Boolean, isPast: Boolean, isFuture: Boolean,
    modifier: Modifier = Modifier, onClick: () -> Unit
) {
    val bg = when {
        isToday              -> SkyBluePrimary
        hasEvent && isPast   -> Color(0xFFD1FAE5)
        hasEvent && isFuture -> Color(0xFFFEF9C3)
        else                 -> Color.Transparent
    }
    val textColor = when {
        isToday  -> Color.White
        isPast   -> SkyBlueDark
        isFuture -> Color(0xFF92400E)
        else     -> Color(0xFF94A3B8)
    }
    Column(
        modifier = modifier.aspectRatio(1f).padding(2.dp)
            .clip(RoundedCornerShape(10.dp)).background(bg).clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(day.toString(), fontSize = 13.sp,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal, color = textColor)
        if (hasEvent) {
            Spacer(Modifier.height(2.dp))
            Box(modifier = Modifier.size(4.dp).clip(CircleShape)
                .background(if (isFuture) Color(0xFFF59E0B) else Color(0xFF10B981)))
        }
    }
}

@Composable
private fun LegendDot(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
        Text(label, fontSize = 11.sp, color = SkyBlueMedium)
    }
}
