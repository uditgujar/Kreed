    package com.placementadda.vestorgrow.basic.validation

    import android.widget.EditText
    import android.widget.TextView
    import com.uditpatidar.kreed.basic.validation.Validation

    class ValidationModel {
        var type: Validation.Type? = null
        var field: String? = null
        var editText: EditText? = null
        var editText1: EditText? = null
        var errorMessage: String? = null
        var errorTextView: TextView? = null
        var Parameter: String? = null

        constructor()
        constructor(type: Validation.Type?, editText: EditText?, editText1: EditText?) {
            this.type = type
            this.editText = editText
            this.editText1 = editText1
        }

        constructor(type: Validation.Type?, field: String?, errorMessage: String?) {
            this.type = type
            this.field = field
            this.errorMessage = errorMessage
        }

        constructor(type: Validation.Type?, editText: EditText?) {
            this.type = type
            this.editText = editText
        }

        constructor(
            type: Validation.Type?,
            field: String?,
            editText: EditText?,
            editText1: EditText?,
            errorMessage: String?,
            errorTextView: TextView?,
            Parameter: String?
        ) {
            this.type = type
            this.field = field
            this.editText = editText
            this.editText1 = editText1
            this.errorMessage = errorMessage
            this.errorTextView = errorTextView
            this.Parameter = Parameter
        }

        constructor(
            type: Validation.Type?,
            editText: EditText?,
            editText1: EditText?,
            errorTextView: TextView?
        ) {
            this.type = type
            this.editText = editText
            this.editText1 = editText1
            this.errorTextView = errorTextView
        }

        constructor(
            type: Validation.Type?,
            field: String?,
            errorMessage: String?,
            errorTextView: TextView?
        ) {
            this.type = type
            this.field = field
            this.errorMessage = errorMessage
            this.errorTextView = errorTextView
        }

        constructor(type: Validation.Type?, editText: EditText?, errorTextView: TextView?) {
            this.type = type
            this.editText = editText
            this.errorTextView = errorTextView
        }

        constructor(
            type: Validation.Type?,
            editText: EditText?,
            editText1: EditText?,
            errorTextView: TextView?,
            Parameter: String?
        ) {
            this.type = type
            this.editText = editText
            this.editText1 = editText1
            this.errorTextView = errorTextView
            this.Parameter = Parameter
        }

        constructor(
            type: Validation.Type?,
            field: String?,
            errorMessage: String?,
            errorTextView: TextView?,
            Parameter: String?
        ) {
            this.type = type
            this.field = field
            this.errorMessage = errorMessage
            this.errorTextView = errorTextView
            this.Parameter = Parameter
        }

        constructor(
            type: Validation.Type?,
            editText: EditText?,
            errorTextView: TextView?,
            Parameter: String?
        ) {
            this.type = type
            this.editText = editText
            this.errorTextView = errorTextView
            this.Parameter = Parameter
        }
    }