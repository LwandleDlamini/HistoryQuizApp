// Import statements at the top
package vcmsa.lwandle.historyquizapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

data class Question(
    val questionText: String, // The text of the question
    val correctAnswer: String, // The correct answer
    var userAnswer: String? // The user's selected answer (nullable)
) : Serializable

// Your class definition
class ReviewScreen : AppCompatActivity() {

    // onCreate function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviewscreen)

        // Define your views and logic here
        val reviewTextView = findViewById<TextView>(R.id.textView2)

        // Retrieve the questions list
        val questions = intent.getSerializableExtra("REVIEW_QUESTIONS") as? ArrayList<Question>

        if (questions == null || questions.isEmpty()) {
            Toast.makeText(this, "No questions available for review.", Toast.LENGTH_SHORT).show()
            return
        }

        val reviewText = StringBuilder()

        // Loop through the questions and build the review text
        questions.forEachIndexed({ index, question ->
            reviewText.append("Question ${index + 1}:\n")
            reviewText.append("${question.questionText}\n")
            reviewText.append("Your Answer: ${question.userAnswer ?: "No answer"}\n")
            reviewText.append("Correct Answer: ${question.correctAnswer}\n")
            reviewText.append("\n")
        })

        // Display the review text
        reviewTextView.text = reviewText.toString()
        reviewTextView.textSize = 16f
    }
}
