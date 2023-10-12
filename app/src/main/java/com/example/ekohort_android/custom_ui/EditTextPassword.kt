package com.example.ekohort_android.custom_ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

import androidx.core.content.ContextCompat
import com.example.ekohort_android.R

class EditTextPassword : AppCompatEditText, View.OnTouchListener{

    private lateinit var eyeButtonImage: Drawable

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAfter: Int): super (context, attrs, defStyleAfter){
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }

    private fun init(){
        eyeButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_eye_disable) as Drawable

        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, onChange: Int, after: Int) { //state when user do nothing/before typing
                if (s.toString().isNotEmpty() ) showEyeButton()
            //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, onChange: Int) {
                if (s.toString().isNotEmpty()) showEyeButton()
                if (s.toString().length < 8) context.getString(R.string.password_warning) //state when user do a thing shit
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().length < 6 ) showError() //when the password got typed
            }
        })
    }

    private fun showError(){
        error = context.getString(R.string.password_warning)
    }

    private fun showEyeButton(){
        setButtonDrawables(endOfTheText = eyeButtonImage) //this used for insert image button to the end
    }

    private fun hideEyeButton(){
        setButtonDrawables()
    }

    private fun setButtonDrawables(startOfTheText: Drawable? = null, topOfTheText:Drawable? = null, endOfTheText:Drawable? = null, bottomOfTheText: Drawable? = null) {
        //set the drawables/icon to the each side
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText,topOfTheText,endOfTheText,bottomOfTheText)

    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean { //logic for touch
        if (compoundDrawables[2] != null) {
            val eyeButtonStart: Float
            val eyeButtonEnd: Float
            var isEyeButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                eyeButtonEnd = (eyeButtonImage.intrinsicWidth + paddingStart).toFloat()
                if (event.x < eyeButtonEnd) isEyeButtonClicked = true
            } else {
                eyeButtonStart = (width - paddingEnd - eyeButtonImage.intrinsicWidth).toFloat()
                if (event.x > eyeButtonStart) isEyeButtonClicked = true
            }

            if (isEyeButtonClicked) {
                return when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        hideEyeButton()
                        if (transformationMethod != null){
                            if (transformationMethod.equals(HideReturnsTransformationMethod.getInstance())) {
                                transformationMethod = PasswordTransformationMethod.getInstance()
                                eyeButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_eye_disable) as Drawable
                                showEyeButton()
                            }
                        }
                         else {
                            transformationMethod = HideReturnsTransformationMethod.getInstance()
                            eyeButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_eye_enable) as Drawable
                            showEyeButton()
                        }
                        true
                    }
                    else -> false
                }
            } else return false
        }
        return false
    }
}