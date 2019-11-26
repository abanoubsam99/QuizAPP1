package com.example.testquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContact.QuestionsTable.TABLE_NAME + " ( " +
                QuizContact.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContact.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContact.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContact.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContact.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContact.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  QuizContact.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Questions q1 = new Questions("Question ..........( A is correct)", "A", "B", "C", 1);
        addQuestion(q1);
        Questions q2 = new Questions("Question ..........( B is correct )", "A", "B", "C", 2);
        addQuestion(q2);
        Questions q3 = new Questions("Question ..........( C is correct )", "A", "B", "C", 3);
        addQuestion(q3);
        Questions q4 = new Questions("Question ..........( A is correct ) ", "A", "B", "C", 1);
        addQuestion(q4);
        Questions q5 = new Questions("Question ..........( B is correct )", "A", "B", "C", 2);
        addQuestion(q5);
        Questions q6 = new Questions("Question ..........( a is correct )", "A", "B", "C", 1);
        addQuestion(q6);
        Questions q7 = new Questions("Question ..........( b is correct )", "A", "B", "C", 2);
        addQuestion(q7);
        Questions q8 = new Questions("Question ..........( c is correct )", "A", "B", "C", 3);
        addQuestion(q8);
    }

    private void addQuestion(Questions question) {
        ContentValues cv = new ContentValues();
        cv.put( QuizContact.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put( QuizContact.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put( QuizContact.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put( QuizContact.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put( QuizContact.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert( QuizContact.QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Questions> getAllQuestions() {
        ArrayList<Questions> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContact.QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Questions question = new Questions();
                question.setQuestion(c.getString(c.getColumnIndex(QuizContact.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContact.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContact.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContact.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContact.QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

}


