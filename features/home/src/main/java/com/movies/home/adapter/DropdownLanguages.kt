package com.movies.home.adapter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.movies.home.EN_US
import com.movies.home.ES_CO
import com.movies.home.PT_BR
import com.movies.home.R

@Composable
fun CustomDropDownLanguages(languageIdSelected: String, onChangeRecommendedMovies: (String) -> Unit) {
    val languages = listOf(
        LanguageItem(ES_CO, stringResource(id = R.string.label_in_spanish)),
        LanguageItem(EN_US, stringResource(id = R.string.label_in_english)),
        LanguageItem(PT_BR, stringResource(id = R.string.label_in_portuguese)),
    )
    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.padding(start = 16.dp)) {
        TextFieldDropDown(
            languageSelected = languages.first { it.id == languageIdSelected }.name,
            toggleDropDown = { isDropdownExpanded = it },
        )
        DropDownLanguages(
            isDropdownExpanded = isDropdownExpanded,
            languageSelected = languageIdSelected,
            languages = languages,
            toggleDropDown = { isDropdownExpanded = it }
        ) {
            onChangeRecommendedMovies.invoke(it.id)
        }
    }
}

@Composable
fun TextFieldDropDown(
    languageSelected: String,
    toggleDropDown: (Boolean) -> Unit,
) {
    TextField(
        value = languageSelected,
        enabled = false,
        onValueChange = { },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.White
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White,
            disabledTextColor = Color.White,
            disabledIndicatorColor = Color.White
        ),
        modifier = Modifier
            .width(150.dp)
            .clickable { toggleDropDown(true) }
    )
}

@Composable
fun DropDownLanguages(
    isDropdownExpanded: Boolean,
    languageSelected: String,
    languages: List<LanguageItem>,
    toggleDropDown: (Boolean) -> Unit,
    onSelectLanguage: (LanguageItem) -> Unit
) {
    DropdownMenu(
        expanded = isDropdownExpanded,
        onDismissRequest = { toggleDropDown(false) }
    ) {
        languages.forEach { language ->
            DropdownMenuItem(
                onClick = {
                    onSelectLanguage(language)
                    toggleDropDown(false)
                },
                modifier = Modifier.background(
                    if (languageSelected == language.id)
                        Color.LightGray
                    else
                        Color.White
                )
            ) { Text(text = language.name) }
        }
    }
}

data class LanguageItem(
    val id: String,
    val name: String
)