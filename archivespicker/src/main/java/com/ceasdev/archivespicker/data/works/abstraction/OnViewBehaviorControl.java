package com.ceasdev.archivespicker.data.works.abstraction;
import android.view.View;
import androidx.annotation.NonNull;

/**Manipular o comportamento de uma view**/
 
public interface OnViewBehaviorControl<VIEW extends View> {
    
    /**Quando considerada vazia**/
    @NonNull
    VIEW onViewEmptyBehavior(VIEW view);
    /**Quando considerada preenchida**/
    @NonNull
    VIEW onViewFullBehavior(VIEW view);
    
}
