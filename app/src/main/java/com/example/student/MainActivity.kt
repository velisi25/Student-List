package com.example.student

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.student.ui.theme.StudentTheme

class MainActivity : ComponentActivity() {
    private var selectedStudentName by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val studentNames = listOf(
            "John Doe",
            "Alexia",
            "Bob Smith",
            "Maxwell",
            "Newton",
            "Messy",
            "Jessie",
            "Thomas",
            "Sam",
            "Karan"
        )
        setContent {
            StudentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudentListAndDetails(studentNames = studentNames,
                        selectedStudentName = selectedStudentName,
                        onStudentSelected = { selectedStudentName = it }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentList(studentNames:List<String>){
    var selectedStudentName by remember { mutableStateOf<String?>(null) }
    if(selectedStudentName==null) {
        Column {
            TopAppBar(
                title = {
                    Text(text = "Student List")
                }
            )
            LazyColumn {
                items(studentNames) { studentName ->
                    Text(
                        text = studentName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                selectedStudentName = studentName
                            }
                    )

                }
            }
        }
    }else {
        // Display student details instead of the list
        val studentDetails = getStudentdetails(selectedStudentName)
        if (studentDetails != null) {
            StudentDetailsScreen(studentDetails) {
                selectedStudentName = null // Navigate back to the list
            }
        }
    }

}

private fun getStudentdetails(studentName: String?): Studentdetails? {
    val studentDetailsList = listOf(
        Studentdetails("John Doe", "john@example.com", "123-456-7890", "Peter Doe", 25),
        Studentdetails("Alexia","alexia@example.com","234-567-890","Samuel",22),
        Studentdetails("Bob Smith","bob@example.com","123-567-890","Robin Smith",21),
        Studentdetails("Maxwell","maxwell@edu.in","456-908-567","Finnwell",20),
        Studentdetails("Newton","newton@example.com","345-209-678","Rohan",21),
        Studentdetails("Messy","messy@gmail.com","768-903-234","Tim",23),
        Studentdetails("Jessie","jessie@example.com","534-890-123","Fred",23),
        Studentdetails("Thomas","thomas@example.com","345-098-765","Edison",22),
        Studentdetails("Sam","sam@gmail.com","345-098-321","Kiran",22),
        Studentdetails("Karan","karan@gmail.com","456-654-345","Johar",23)

    )

    return studentDetailsList.firstOrNull { it.name == studentName }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListAndDetails(studentNames: List<String>, selectedStudentName: String?, onStudentSelected: (String) -> Unit) {
    val isLandscape = isLandscape()

    if (isLandscape) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                TopAppBar(
                    title = { Text(text = "Student List") }
                )
                LazyColumn {
                    items(studentNames) { studentName ->
                        Text(
                            text = studentName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    onStudentSelected(studentName)
                                }
                        )
                    }
                }
            }

            if (selectedStudentName != null) {
                val studentDetails = getStudentdetails(selectedStudentName)
                if (studentDetails != null) {
                    Box(
                        modifier = Modifier.weight(1f)
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
                    ) {
                        StudentDetails(studentDetails)
                    }
                }
            }
        }
    } else {
        // Handle portrait mode behavior here
        // You can navigate to a different screen or use a separate Composable
        StudentList(studentNames)
    }
}

@Composable
fun StudentDetails(studentDetails: Studentdetails) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Name: ${studentDetails.name}")
        Text(text = "Email: ${studentDetails.email}")
        Text(text = "Phone Number: ${studentDetails.phoneno}")
        Text(text = "Father's Name: ${studentDetails.fathername}")
        Text(text = "Age: ${studentDetails.age}")
    }
}

@Composable
private fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}


@Preview
@Composable
fun StudentListPreview(){
    val studentNames = listOf(
        "John Doe",
        "Alexia",
        "Bob Smith",
        "Maxwell",
        "Newton",
        "Messy",
        "Jessie",
        "Thomas",
        "Sam",
        "Karan"
    )
    StudentList(studentNames = studentNames)
}
