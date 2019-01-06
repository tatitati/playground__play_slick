package application.Worker

import application.Speaker.WorkerInt

class DeveloperWorker() extends WorkerInt
{
  def sayWork(): String = {
    s"I'm a developer worker"
  }
}






