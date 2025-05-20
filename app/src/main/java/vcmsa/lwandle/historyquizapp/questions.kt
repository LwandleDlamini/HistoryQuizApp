package vcmsa.lwandle.historyquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Questions : AppCompatActivity() {

    private var counter = 0
    private val historyQuestions = arrayOf(
        "The Cold War was primarily a conflict of ideology between capitalism and communism.",
        "The United States supported the spread of communism during the Cold War.",
        "The Berlin Wall was a symbol of division between East and West during the Cold War.",
        "The Cuban Missile Crisis occurred in 1989 at the end of the Cold War.",
        "The arms race between the USA and USSR led to the development of nuclear weapons on both sides."
    )

    private val answerChoices = arrayOf(
        arrayOf("True", "False"),
        arrayOf("True", "False"),
        arrayOf("True", "False"),
        arrayOf("True", "False"),
        arrayOf("True", "False")
    )

    private val correctAnswers = arrayOf("True", "False", "True", "False", "True")
    private val userAnswers = arrayOfNulls<String>(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val rbtngAnswers = findViewById<RadioGroup>(R.id.rbtngQuizAnswers)
        val btnNext = findViewById<Button>(R.id.btnNext)

        displayQuestion(tvQuestion, rbtngAnswers)

        btnNext.setOnClickListener {
            val selectedId = rbtngAnswers.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                userAnswers[counter] = selectedRadioButton.text.toString()
                counter++

                if (counter < historyQuestions.size) {
                    displayQuestion(tvQuestion, rbtngAnswers)
                } else {
                    val score = calculateScore()
                    val feedback = when {
                        score == 5 -> "Excellent work!"
                        score >= 3 -> "Good job, keep practicing!"
                        else -> "Try again and keep learning!"
                    }

                    val intent = Intent(this, scoreScreen::class.java)
                    intent.putExtra("SCORE", score)
                    intent.putExtra("FEEDBACK", feedback)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayQuestion(tvQuestion: TextView, rbtngAnswers: RadioGroup) {
        tvQuestion.text = historyQuestions[counter]
        rbtngAnswers.clearCheck()

        for (i in 0 until rbtngAnswers.childCount) {
            val radioButton = rbtngAnswers.getChildAt(i) as RadioButton
            radioButton.text = answerChoices[counter][i]
        }
    }

    private fun calculateScore(): Int {
        var score = 0
        for (i in correctAnswers.indices) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++
            }
        }
        return score
    }
}
