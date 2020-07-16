// Original file from https://github.com/kolovos/markingmate
rule Exam 
	match l : ALICE!Exam
	with r : BOB!Exam {

	compare : true
}

rule Question 
	match l : ALICE!Question
	with r : BOB!Question {

	compare : l.title = r.title
}

rule Answer 
	match l : ALICE!Answer
	with r : BOB!Answer {

	compare : l.question.matches(r.question) 
		and l.student.matches(r.student)
}

rule Student 
	match l : ALICE!Student
	with r : BOB!Student {

	compare : l.number = r.number
}