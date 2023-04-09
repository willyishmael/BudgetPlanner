package com.troubleshouters.budgetplanner.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.troubleshouters.budgetplanner.data.local.plan.Plan
import com.troubleshouters.budgetplanner.data.repository.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val planRepository: PlanRepository
) : ViewModel() {

    private val _planLiveData = MutableLiveData<Plan?>()
    val planLiveData: LiveData<Plan?>
        get() = _planLiveData

    val allPlans: LiveData<List<Plan>> = liveData {
        val plans = planRepository.getAllPlans()
        emit(plans)
    }

    fun getPlanById(planId: Long) {
        viewModelScope.launch {
            val plan = planRepository.getPlanById(planId)
            _planLiveData.postValue(plan)
        }
    }

    fun getPlanBudgetForAMonth(): LiveData<Double> = planRepository.getPlanBudgetForAMonth()

    fun insertPlan(plan: Plan) {
        viewModelScope.launch {
            planRepository.insertPlan(plan)
        }
    }

    fun updatePlan(plan: Plan) {
        viewModelScope.launch {
            planRepository.updatePlan(plan)
        }
    }

    fun deletePlan(plan: Plan) {
        viewModelScope.launch {
            planRepository.deletePlan(plan)
        }
    }

}