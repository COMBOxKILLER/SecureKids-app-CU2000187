package com.example.parentguide.navigation.KidUser

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.parentguide.Models.KidData
import com.example.parentguide.Models.KidNotifications
import com.example.parentguide.Models.Notifications
import com.example.parentguide.Models.ParentNotifications
import com.example.parentguide.Models.QuizScore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.security.MessageDigest
import java.time.LocalDateTime

@Composable
fun CreateKidUser(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid
    val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid.toString()).child("kidsUsers")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBACAE7)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(
            modifier = Modifier
                .background(Color(0xFFBACAE7))
                .fillMaxSize()
        ) {
            item(1000) {

                val username = remember { mutableStateOf(TextFieldValue()) }
                val password = remember { mutableStateOf(TextFieldValue()) }
                val passwordConfirm = remember { mutableStateOf(TextFieldValue()) }
                var isPasswordVisible by remember { mutableStateOf(false) }
                val age = remember { mutableStateOf(TextFieldValue()) }
                val dailyLoginHours = remember { mutableStateOf(TextFieldValue()) }
                val initialCoins = remember { mutableStateOf(TextFieldValue()) }
                var selectedGender by remember { mutableStateOf("") }
                val genders = listOf("Male", "Female")
                var errorMessage by remember { mutableStateOf<String?>(null) }
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                            verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ,
                ) {
                    Text(
                        text = "Create Kid User",
                        style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive)
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        label = { Text(text = "Username") },
                        value = username.value,
                        onValueChange = { username.value = it }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        label = { Text(text = "Password") },
                        value = password.value,
                        onValueChange = { password.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val iconImage = if (isPasswordVisible) {
                                Icons.Filled.Visibility

                            } else {
                                Icons.Filled.VisibilityOff
                            }
                            var description = if (isPasswordVisible) {
                                "Hide Password"
                            } else {
                                "Show Password"
                            }
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = iconImage,
                                    contentDescription = description,
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        label = { Text(text = "Password Confirmation") },
                        value = passwordConfirm.value,
                        onValueChange = { passwordConfirm.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val iconImage = if (isPasswordVisible) {
                                Icons.Filled.Visibility

                            } else {
                                Icons.Filled.VisibilityOff
                            }
                            var description = if (isPasswordVisible) {
                                "Hide Password"
                            } else {
                                "Show Password"
                            }
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = iconImage,
                                    contentDescription = description,
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        label = { Text(text = "Age") },
                        value = age.value,
                        onValueChange = { age.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                        )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        label = { Text(text = "Daily Hours Usage") },
                        value = dailyLoginHours.value,
                        onValueChange = { dailyLoginHours.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),


                        )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        label = { Text(text = "Intial Coins") },
                        value = initialCoins.value,
                        onValueChange = { initialCoins.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Select Gender")

                        genders.forEach { gender ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                RadioButton(
                                    selected = gender == selectedGender,
                                    onClick = { selectedGender = gender }
                                )
                                Text(
                                    text = gender,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    // Display error message
                    errorMessage?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            // Reset error message
                            errorMessage = null

                            databaseReference.addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                @RequiresApi(Build.VERSION_CODES.O)
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    val count = dataSnapshot.childrenCount

                                    // Check for maximum 4 kids
                                    if (count >= 4) {
                                        errorMessage = "Maximum of 4 kids reached"
                                        return
                                    }

                                    // Validation logic
                                    if (username.value.text.isBlank() || password.value.text.isBlank() || age.value.text.isBlank()) {
                                        errorMessage = "Please fill all the fields"
                                        return
                                    }

                                    if (password.value.text != passwordConfirm.value.text) {
                                        errorMessage = "Passwords do not match"
                                        return
                                    }
                                    val hashedPassword = hashPassword(password.value.text)
                                    // Generate a unique key for the new kid
                                    val uniqueId = databaseReference.push().key
                                        ?: return  // Get a unique ID and return if null


                                    // Create kid user if validation passes
                                    val kidUser = KidData(
                                        uniqueId,
                                        username.value.text,
                                        hashedPassword,
                                        age.value.text.toIntOrNull()
                                            ?: 0, // Ensure age is an integer
                                        dailyLoginHours.value.text.toIntOrNull() ?: 0,
                                        initialCoins.value.text.toIntOrNull() ?: 0,
                                        selectedGender,
                                        quizzes = listOf(QuizScore(hasSolved = "notSolved",score = 0, tryCount = 0)),
                                        totalCoins = initialCoins.value.text.toIntOrNull() ?: 0,
                                    )

                                    databaseReference.child(uniqueId).setValue(kidUser)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                context,
                                                "Created kid user",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            navController.navigate("Home")
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                context,
                                                "Failed to create user",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Handle error
                                }
                            })
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Create")
                    }
                }

            }

        }
    }

}
fun hashPassword(password: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(password.toByteArray(Charsets.UTF_8))
    return hash.joinToString("") { "%02x".format(it) }
}
