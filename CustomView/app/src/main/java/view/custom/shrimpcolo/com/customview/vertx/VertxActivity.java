package view.custom.shrimpcolo.com.customview.vertx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import io.vertx.core.Vertx;
import view.custom.shrimpcolo.com.customview.R;

public class VertxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertx);

        Button btnClick = (Button)findViewById(R.id.btn_vertx);
        btnClick.setOnClickListener(v -> Toast.makeText(VertxActivity.this, "Test", Toast.LENGTH_LONG).show());
    }

    public void onClick(View view){
//        Vertx.vertx()
//                .createHttpServer()
//                .requestHandler(req -> req.response().end("Hello World!"))
//                .listen(8080, handler -> {
//                    if (handler.succeeded()) {
//                        System.out.println("http://localhost:8080/");
//                    } else {
//                        System.err.println("Failed to listen on port 8080");
//                    }
//                });
    }
}
