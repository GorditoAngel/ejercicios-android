package igserrano.control.controlesextendidos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextContador extends EditText {

	public EditTextContador(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public EditTextContador(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public EditTextContador(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
	
		Paint pNegro = new Paint(Paint.ANTI_ALIAS_FLAG);
		pNegro.setColor(Color.BLACK);
		pNegro.setStyle(Style.FILL);
		
		Paint pAmarillo = new Paint(Paint.ANTI_ALIAS_FLAG);
		pAmarillo.setColor(Color.YELLOW);
		pAmarillo.setStyle(Style.FILL);
		
		int len = 250 - this.getText().toString().length();

		
		canvas.drawRect(getWidth()-30, 5, getWidth()-5, 23, pNegro);
		
		canvas.drawText(""+len, getWidth()-28, 20, pAmarillo);
	}

}
