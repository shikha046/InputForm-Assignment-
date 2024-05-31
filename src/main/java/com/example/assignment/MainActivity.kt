package com.example.assignment

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.*
import com.example.assignment.Repository.UserRepository
import com.example.assignment.ViewModel.UserViewModelFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.assignment.Database.AppDatabase
import com.example.assignment.Model.User
import com.example.assignment.ViewModel.UserViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the database and DAO
        val database = AppDatabase.getDatabase(this)
        val userDao = database.userDao()
        val userRepository = UserRepository(userDao)

        // Create a factory for UserViewModel
        val factory = UserViewModelFactory(userRepository)
        val userViewModel: UserViewModel by viewModels { factory }

        setContent {
            UserInputScreen(userViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputScreen(userViewModel: UserViewModel) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            dob = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    val saveUser = {
        when {
            name.isEmpty() -> Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
            age.isEmpty() -> Toast.makeText(context, "Please enter your age", Toast.LENGTH_SHORT).show()
            !age.isDigitsOnly() -> Toast.makeText(context, "Age must be a number", Toast.LENGTH_SHORT).show()
            dob.isEmpty() -> Toast.makeText(context, "Please select your date of birth", Toast.LENGTH_SHORT).show()
            address.isEmpty() -> Toast.makeText(context, "Please enter your address", Toast.LENGTH_SHORT).show()
            else -> {
                userViewModel.insertUser(
                    User(
                        name = name,
                        age = age.toInt(),
                        dob = dob,
                        address = address
                    )
                )
                Toast.makeText(context, "User saved successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Information") }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Enter your details",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    singleLine = true

                )

                TextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    singleLine = true
                )

                TextField(
                    value = dob,
                    onValueChange = { dob = it },
                    label = { Text("Date of Birth") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Pick Date"
                            )
                        }
                    },
                    singleLine = true
                )

                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    singleLine = true
                )

                Button(
                    onClick = saveUser,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp)
                        .testTag("Save")
                ) {
                    Text("Save")
                }
            }
        }
    )
}


