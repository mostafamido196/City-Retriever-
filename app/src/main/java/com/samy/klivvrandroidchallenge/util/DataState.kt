package com.samy.mostafasamy.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext


sealed class DataState {

    //idle
    object Idle : DataState()

    //loading
    object Loading : DataState()

    //result
    data class Result<T>(var response: T) : DataState()

    //error
    data class Error(var errorCode: Int, var msg: String? = null) : DataState() {

        fun handleErrors(
            @ApplicationContext
            mContext: Context,
            mDialogsListener: (()->Unit)? = null
        ) {

            Log.e(TAG, "handleErrors: msg $msg")
            Log.e(TAG, "handleErrors: error code $errorCode")

            when (errorCode) {

                Constants.Codes.EXCEPTIONS_CODE -> {
                    showHelperDialog(
                        msg = if (msg.isNullOrEmpty()) "io" else msg!!,
                        mContext = mContext,
                        mDialogsListener = mDialogsListener
                    )
                }
                else -> {
                    showHelperDialog(
                        msg = if (msg.isNullOrEmpty()) "known_error" else msg!!,
                        mContext = mContext,
                        mDialogsListener = mDialogsListener
                    )
                }
            }

        }

        private fun showHelperDialog(
            msg: String,
            mContext: Context,
            mDialogsListener: (()->Unit)?
        ) {

            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
            if (mDialogsListener != null) {
                mDialogsListener()
            }
        }

        companion object {
            private val TAG = this::class.java.name
        }

    }

}
