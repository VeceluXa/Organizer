package com.danilovfa.organizer.tasks.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.icerock.moko.resources.compose.stringResource
import com.danilovfa.organizer.resources.MR

@Composable
fun TimePickerDialog(
    title: String,
    initialHours: Int? = null,
    initialMinutes: Int? = null,
    onDismiss: () -> Unit,
    onTimeSelected: (hours: Int, minutes: Int) -> Unit
) {
    var hours by remember { mutableStateOf(initialHours ?: 0) }
    var minutes by remember { mutableStateOf(initialMinutes ?: 0) }

    Dialog(onDismissRequest = onDismiss) {

        Column(
            modifier = Modifier
                .width(270.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.4.sp
                )
            )

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                NumberInput(
                    maxNumber = 24,
                    initialNumber = initialHours ?: 0,
                    label = stringResource(MR.strings.hour)
                ) {
                    hours = it
                }

                ColonSpacer()

                NumberInput(
                    maxNumber = 59,
                    initialNumber = initialMinutes ?: 0,
                    label = stringResource(MR.strings.minute)
                ) {
                    minutes = it
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(MR.strings.cancel),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                }

                TextButton(onClick = {
                    onTimeSelected(hours, minutes)
                    onDismiss()
                }) {
                    Text(
                        text = stringResource(MR.strings.save),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun ColonSpacer() {
    Column(
        modifier = Modifier
            .width(24.dp)
            .height(72.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = CircleShape
                )
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = CircleShape
                )
        )
    }
}

@Composable
private fun NumberInput(
    maxNumber: Int = 24,
    initialNumber: Int = 0,
    label: String,
    onValueChange: (Int) -> Unit
) {
    var number by remember { mutableStateOf(initialNumber) }

    Column(
        modifier = Modifier.width(96.dp)
    ) {
        var isFocused by remember { mutableStateOf(false) }
        val containerColor = if (isFocused) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }

        val borderColor = if (isFocused) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }

        val textColor = if (isFocused) {
            MaterialTheme.colorScheme.onPrimaryContainer
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

        BasicTextField(
            value = if (number == 0) "00" else number.toString(),
            onValueChange = { numberStr ->
                if (numberStr == "") {
                    number = 0
                    onValueChange(0)
                } else numberStr.toIntOrNull()?.let { newNumber ->
                    val formattedNumber =
                        onNumberChanged(number.toString(), newNumber.toString(), maxNumber)
                    number = formattedNumber.toInt()
                    onValueChange(number)

                }
            },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 45.sp,
                lineHeight = 52.sp,
                fontWeight = FontWeight(400),
                fontFamily = FontFamily.SansSerif,
                color = textColor,
                textAlign = TextAlign.Center
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                autoCorrect = false
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
        ) { innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = containerColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                innerTextField()
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = label,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.4.sp,
            )
        )
    }
}

private fun onNumberChanged(oldValue: String, newValue: String, maxNumber: Int): String {
    if (newValue.length < oldValue.length) return newValue
    if (newValue.toInt() <= maxNumber) return newValue

    val insertedCharPosition = findInsertedCharPosition(oldValue, newValue)

    val formattedValue = if (insertedCharPosition == newValue.lastIndex && newValue.length >= 2) {
        newValue.removeRange(insertedCharPosition - 1 until insertedCharPosition)
    } else if (insertedCharPosition + 1 <= newValue.lastIndex) {
        newValue.removeRange(insertedCharPosition + 1 until insertedCharPosition + 2)
    } else {
        oldValue
    }

    return if (formattedValue.toInt() <= maxNumber) formattedValue else oldValue
}

private fun findInsertedCharPosition(oldValue: String, newValue: String): Int {
    val minLength = minOf(oldValue.length, newValue.length)

    for (i in 0 until minLength) {
        if (oldValue[i] != newValue[i])
            return i
    }

    return minLength
}

@Preview
@Composable
fun TimePicker() {
    TimePickerDialog(
        title = "duration",
        onDismiss = {},
        onTimeSelected = { _, _ -> }
    )
}