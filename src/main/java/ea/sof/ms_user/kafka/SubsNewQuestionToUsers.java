//package ea.sof.ms_user.kafka;
//
//import com.google.gson.Gson;
//import ea.sof.shared.models.Question;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SubsNewQuestionToUsers {
//
//	@KafkaListener(topics = "${topicNewQuestion}", groupId = "${subsNewQuestionToUsers}")
//	public void newQuestion(String message) {
//
//		System.out.println("topicNewQuestionToUsers: New message from topic: " + message);
//
//		Gson gson = new Gson();
//		Question question =  gson.fromJson(message, Question.class);
//
//		System.out.println("SubsNewQuestionCommentToQuestions: As object: " + question);
//	}
//
//}
