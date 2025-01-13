
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.coffee.R
import com.example.coffee.CalculatorActivity
import com.example.coffee.CurrencyConversionActivity
import com.example.coffee.TemperatureConversionActivity
import com.example.coffee.WeightConversionActivity

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Mendapatkan referensi tombol dari layout setelah layout di-inflate
        val btnHotCoffee: Button = rootView.findViewById(R.id.btn_hot_coffee)
        val btnColdCoffee: Button = rootView.findViewById(R.id.btn_cold_coffee)
        val btnOthers: Button = rootView.findViewById(R.id.btn_others)

        // Menambahkan aksi klik pada masing-masing tombol
        btnHotCoffee.setOnClickListener {
            showMessage("Hot Coffee selected")
        }

        btnColdCoffee.setOnClickListener {
            showMessage("Cold Coffee selected")
        }

        btnOthers.setOnClickListener {
            showOthersDialog()
        }

        return rootView
    }

    // Fungsi untuk menampilkan pesan Toast
    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    // Fungsi untuk menampilkan dialog pilihan
    private fun showOthersDialog() {
        val options = arrayOf("Calculator", "Convert Temperature", "Convert Currency", "Convert Weight")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select an Option")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> openActivity(CalculatorActivity::class.java)
                1 -> openActivity(TemperatureConversionActivity::class.java)
                2 -> openActivity(CurrencyConversionActivity::class.java)
                3 -> openActivity(WeightConversionActivity::class.java)
            }
        }
        builder.show()
    }

    // Fungsi untuk membuka aktivitas
    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(requireContext(), activityClass)
        startActivity(intent)
    }
}
