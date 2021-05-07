package com.infinity.EBacSens.retrofit2;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

//    static class JsonUtil {
//        /**
//         * Format json string
//         *
//         * @param jsonStr json string to be formatted
//         * @return Formatted json string
//         */
//        public static String formatJson(String jsonStr) {
//            if (null == jsonStr || "".equals(jsonStr)) return "";
//            StringBuilder sb = new StringBuilder();
//            char last = '\0';
//            char current = '\0';
//            int indent = 0;
//            for (int i = 0; i < jsonStr.length(); i++) {
//                last = current;
//                current = jsonStr.charAt(i);
//                //Encountered {[wrap, indent next line
//                switch (current) {
//                    case '{':
//                    case '[':
//                        sb.append(current);
//                        sb.append('\n');
//                        indent++;
//                        addIndentBlank(sb, indent);
//                        break;
//                    //Encountered}] newline, current line indented
//                    case '}':
//                    case ']':
//                        sb.append('\n');
//                        indent--;
//                        addIndentBlank(sb, indent);
//                        sb.append(current);
//                        break;
//                    //Encountered, line feed
//                    case ',':
//                        sb.append(current);
//                        if (last != '\\') {
//                            sb.append('\n');
//                            addIndentBlank(sb, indent);
//                        }
//                        break;
//                    default:
//                        sb.append(current);
//                }
//            }
//            return sb.toString();
//        }
//
//        /**
//         * Add space
//         *
//         * @param sb
//         * @param indent
//         */
//        private static void addIndentBlank(StringBuilder sb, int indent) {
//            for (int i = 0; i < indent; i++) {
//                sb.append('\t');
//            }
//        }
//        /**
//         * http Request data to return Chinese characters in json as unicode code to Chinese character transcoding
//         *
//         * @param theString
//         * @return The result of transformation
//         */
//        public static String decodeUnicode(String theString) {
//            char aChar;
//            int len = theString.length();
//            StringBuffer outBuffer = new StringBuffer(len);
//            for (int x = 0; x < len; ) {
//                aChar = theString.charAt(x++);
//                if (aChar == '\\') {
//                    aChar = theString.charAt(x++);
//                    if (aChar == 'u') {
//                        int value = 0;
//                        for (int i = 0; i < 4; i++) {
//                            aChar = theString.charAt(x++);
//                            switch (aChar) {
//                                case '0':
//                                case '1':
//                                case '2':
//                                case '3':
//                                case '4':
//                                case '5':
//                                case '6':
//                                case '7':
//                                case '8':
//                                case '9':
//                                    value = (value << 4) + aChar - '0';
//                                    break;
//                                case 'a':
//                                case 'b':
//                                case 'c':
//                                case 'd':
//                                case 'e':
//                                case 'f':
//                                    value = (value << 4) + 10 + aChar - 'a';
//                                    break;
//                                case 'A':
//                                case 'B':
//                                case 'C':
//                                case 'D':
//                                case 'E':
//                                case 'F':
//                                    value = (value << 4) + 10 + aChar - 'A';
//                                    break;
//                                default:
//                                    throw new IllegalArgumentException(
//                                            "Malformed   \\uxxxx   encoding.");
//                            }
//
//                        }
//                        outBuffer.append((char) value);
//                    } else {
//                        if (aChar == 't')
//                            aChar = '\t';
//                        else if (aChar == 'r')
//                            aChar = '\r';
//                        else if (aChar == 'n')
//                            aChar = '\n';
//                        else if (aChar == 'f')
//                            aChar = '\f';
//                        outBuffer.append(aChar);
//                    }
//                } else
//                    outBuffer.append(aChar);
//            }
//            return outBuffer.toString();
//        }
//
//    }
//
//    static class HttpLogger implements HttpLoggingInterceptor.Logger {
//        private StringBuilder mMessage = new StringBuilder();
//        private static final String TAG = "HttpLogger";
//        @Override
//        public void log(String message) {
//            // Request or response start
//            if (message.startsWith("--> POST")) {
//                mMessage.setLength(0);
//            }
//            // The description in the form of {} or [] is the json data of the response result, which needs to be formatted
//            if ((message.startsWith("{") && message.endsWith("}"))
//                    || (message.startsWith("[") && message.endsWith("]"))) {
//                message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
//            }
//            mMessage.append(message.concat("\n"));
//            // End of response, print the whole log
//            if (message.startsWith("<-- END HTTP")) {
//                Log.d(TAG,mMessage.toString());
//            }
//        }
//    }


    public static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl){

        //HttpLogger httpLogger = new HttpLogger();
        //HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(httpLogger);
        //loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient builder = new OkHttpClient.Builder()
                .readTimeout(60 , TimeUnit.SECONDS)
                .writeTimeout(60 , TimeUnit.SECONDS)
                .connectTimeout(60 , TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .addNetworkInterceptor(loggingInterceptor)
//                .addInterceptor(loggingInterceptor)
                .build();



        //Gson gson = new GsonBuilder().setLenient().create();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }


}
