package ir.fararayaneh.erp.activities.itruducer_act

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage
import ir.fararayaneh.erp.App
import ir.fararayaneh.erp.R
import ir.fararayaneh.erp.commons.SHOW_GLOBAL_INTRO
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider
import ir.fararayaneh.erp.utils.ContextHelper
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler


class IntroHelp : AppIntro() {

    private var wichIntro: Int = SHOW_GLOBAL_INTRO //default intro

    init {
        setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupThem()
        super.onCreate(savedInstanceState)
        wichIntro = IntentReceiverHandler.getIntentData(intent.extras).someInt

        showProperSlider()

    }

    private fun showProperSlider() {
        when (wichIntro) {
            SHOW_GLOBAL_INTRO -> showGlobalIntro()
        }
    }

    private fun showGlobalIntro() {
        addSlide(AppIntroFragment.newInstance(sliderBuilder("",baseContext.getString(R.string.well_come_app),R.drawable.ic_helper_8,Color.parseColor("#86D5FE"),Color.WHITE,Color.WHITE)))
        addSlide(AppIntroFragment.newInstance(sliderBuilder(baseContext.getString(R.string.intro_title_5),baseContext.getString(R.string.intro_desc_1),R.drawable.ic_helper_3,Color.parseColor("#546471"),Color.WHITE,Color.WHITE)))
        addSlide(AppIntroFragment.newInstance(sliderBuilder(baseContext.getString(R.string.intro_title_1),baseContext.getString(R.string.intro_desc_2),R.drawable.ic_helper_1,Color.parseColor("#BCD96C"),Color.WHITE,Color.WHITE)))
        addSlide(AppIntroFragment.newInstance(sliderBuilder(baseContext.getString(R.string.intro_title_4),baseContext.getString(R.string.intro_desc_3),R.drawable.ic_helper_4,Color.parseColor("#2A2E49"),Color.WHITE,Color.WHITE)))
        addSlide(AppIntroFragment.newInstance(sliderBuilder(baseContext.getString(R.string.intro_title_3),baseContext.getString(R.string.intro_desc_5),R.drawable.ic_helper_7,Color.RED,Color.WHITE,Color.WHITE)))
        addSlide(AppIntroFragment.newInstance(sliderBuilder(baseContext.getString(R.string.intro_title_6),baseContext.getString(R.string.intro_desc_6),R.drawable.ic_helper_9,Color.parseColor("#222631"),Color.WHITE,Color.WHITE)))
        addSlide(AppIntroFragment.newInstance(sliderBuilder(baseContext.getString(R.string.intro_title_2),baseContext.getString(R.string.intro_desc_4),R.drawable.ic_helper_6,Color.parseColor("#FFAA10"),Color.WHITE,Color.WHITE)))
        setSeparatorColor(Color.WHITE)
        setFlowAnimation()
        showSkipButton(false)

        //setBarColor(Color.parseColor("#fc4503"))
        //setFadeAnimation()
        //setZoomAnimation()
        //setSlideOverAnimation()
        //setDepthAnimation()
    }

    private fun sliderBuilder(title: String, description: String, imageDrawable: Int, bgColor: Int, titleColor: Int, descColor: Int): SliderPage {
        val sliderPage = SliderPage()
        sliderPage.title = title
        sliderPage.description = description
        sliderPage.imageDrawable = imageDrawable
        sliderPage.bgColor = bgColor
        sliderPage.titleColor = titleColor
        sliderPage.descColor = descColor

        return sliderPage
    }


    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Do something when users tap on Done button.
        ActivityIntents.resultOkFromIntroduceHelper(this)
    }

    /*override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)

    }*/

    /*override fun onSlideChanged(@Nullable oldFragment: Fragment?, @Nullable newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
        // Do something when the slide changes.
    }*/


    //----------------------------------------------------------------------------------------------
    private fun setupThem() {
        setTheme(SharedPreferenceProvider.getAppTheme(this))
        AppCompatDelegate.setDefaultNightMode(SharedPreferenceProvider.getNightMode(this))
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ContextHelper.customContext(newBase))
    }

    override fun onStart() {
        super.onStart()
        App.addToAppPowerOnList()
    }


    override fun onDestroy() {
        App.removeFromAppPowerOnList()
        super.onDestroy()
    }


}
