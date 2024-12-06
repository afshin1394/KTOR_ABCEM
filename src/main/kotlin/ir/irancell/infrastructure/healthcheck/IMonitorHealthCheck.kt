package ir.irancell.infrastructure.healthcheck

import ir.irancell.infrastructure.InfraElement

interface IMonitorHealthCheck {
    fun checkHealth(onRetainState : (infraElement : InfraElement) -> Unit)
}