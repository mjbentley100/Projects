/**
 * @file bmp.c
 * @author Mason Bentley
 * @date 4/08/2019
 * @brief Performs simple graphical operations on a bitmap file
 * @bugs small graphical errors in output
 */


#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <assert.h>



ssize_t read_all(int fd, void * const buf, size_t n);

#pragma pack(push, 1)
typedef struct bmp_file_header {
	unsigned short * bftype;
	unsigned int * bfsize;
	unsigned short * bfres1;
	unsigned short * bfres2;
	unsigned int * offset;
} bmp_file_header;
#pragma pack(pop)


#pragma pack(push, 1)
typedef struct bmp_info_header {
	unsigned int * bisize;
	unsigned int * biwidth;
	unsigned int * biheight;
	unsigned short * biplanes;
	unsigned short * bibitcount;
	unsigned int * bicompression;
	unsigned int * bisizeimage;
	unsigned int * biXpermeter;
	unsigned int * biYpermeter;
	unsigned int * biclrused;
	unsigned int * biclrimportant;
} bmp_info_header;
#pragma pack(pop)


typedef struct bmp_RGB {
	unsigned char blue;
	unsigned char green;
	unsigned char red;
	unsigned char alpha;
}bmp_RGB;


void print_header(struct bmp_file_header file_header, struct bmp_info_header info_header);
void write_header(struct bmp_file_header file_header, struct bmp_info_header info_header, int out);

int main(int argc, char * argv[]) {

	int op = 0;
	//long PAGESIZE = sysconf(_SC_PAGESIZE);
	void * const buf = malloc(1024);
	int j = 0;
	int col = 0;
	int i = 0;
	int k = 0;
	int offset = 0;
	int array_count = 0;
	char * input_file = argv[1];
	char * output_file = "out.bmp";
	int iflag = 0;
	int rflag = 0;
	int gflag = 0;
	int bflag = 0;
	int dflag = 0;
	int Rflag = 0;
	unsigned char red_val;
	unsigned char green_val;
	unsigned char blue_val;
	int array_count_8 = 0;
	int num_pixels = 0;
	

	/*init structs*/
	struct bmp_file_header file_header;
	struct bmp_info_header info_header;
	struct bmp_RGB rgb[1000000];
	struct bmp_RGB rgb_8[256];
	unsigned char pixel_8[1000000];

	/*parsing input*/
	while ((op = getopt(argc, argv, "o:idRr:g:b:")) != -1) {

		switch (op) {
			case 'o':
				output_file = optarg;
				break;
			case 'i':
				iflag = 1;
				break;
			case 'd':
				dflag = 1;
				break;
			case 'R':
				Rflag = 1;
				break;
			case 'r':
				rflag = 1;
				red_val = atoi(optarg);
				break;
			case 'g':
				gflag = 1;
				green_val = atoi(optarg);
				break;
			case 'b':
				bflag = 1;
				blue_val = atoi(optarg);
				break;
			default:
				break;
		}
	}

	/*opening and reading in file*/
	int fd = open(input_file, O_RDWR | O_APPEND, 0644);
	read_all(fd, buf, 54);
	char * tmp = buf;

	/*Putting data into file header structure*/
	file_header.bftype = malloc(sizeof(short *));
	file_header.bfsize = malloc(sizeof(int *));
	file_header.bfres1 = malloc(sizeof(short *));
	file_header.bfres2 = malloc(sizeof(short *));
	file_header.offset = malloc(sizeof(int *));
	memcpy(file_header.bftype, tmp, 2);
	memcpy(file_header.bfsize, tmp+2, 4);
	memcpy(file_header.bfres1, tmp+6, 2);
	memcpy(file_header.bfres2, tmp+8, 2);
	memcpy(file_header.offset, tmp+10, 4);
	
	/*Putting data into info header structure*/
	info_header.bisize = malloc(sizeof(int *));;
	info_header.biwidth = malloc(sizeof(int *));;
	info_header.biheight = malloc(sizeof(int *));;
	info_header.biplanes = malloc(sizeof(short *));;
	info_header.bibitcount = malloc(sizeof(short *));;
	info_header.bicompression = malloc(sizeof(int *));;
	info_header.bisizeimage = malloc(sizeof(int *));;
	info_header.biXpermeter = malloc(sizeof(int *));;
	info_header.biYpermeter = malloc(sizeof(int *));;
	info_header.biclrused = malloc(sizeof(int *));;
	info_header.biclrimportant = malloc(sizeof(int *));;
	memcpy(info_header.bisize, tmp+14, 4);
	memcpy(info_header.biwidth, tmp+18, 4);
	memcpy(info_header.biheight, tmp+22, 4);
	memcpy(info_header.biplanes, tmp+26, 2);
	memcpy(info_header.bibitcount, tmp+28, 2);
	memcpy(info_header.bicompression, tmp+30, 4);
	memcpy(info_header.bisizeimage, tmp+34, 4);
	memcpy(info_header.biXpermeter, tmp+38, 4);
	memcpy(info_header.biYpermeter, tmp+42, 4);
	memcpy(info_header.biclrused, tmp+46, 4);
	memcpy(info_header.biclrimportant, tmp+50, 4);

	/*Tests for 8bit format (if true reads in color table and pixel data)*/
	if (*info_header.bibitcount == 8) {
		read_all(fd, buf, 1024);		
		tmp = buf;
		int offset_8 = 0;
		array_count_8 = 0;
		/*reading in color table*/
		for (i = 0; i <= 1024; i++) {
			if (i%4 == 0) {
				rgb_8[i - offset_8].blue = tmp[i % 1024] & 255;
				offset_8++;
			} else if (i%4 == 1) {
				rgb_8[i - offset_8].green = tmp[i % 1024] & 255;
				offset_8++;
			} else if (i%4 == 2) {
				rgb_8[i - offset_8].red = tmp[i % 1024] & 255;
				offset_8++;
			} else if (i%4 == 3) {
				rgb_8[i - offset_8].alpha = tmp[i % 1024] &255;				
				array_count_8++;
			}
		}
		/*reading in pixel data*/
		read_all(fd, buf, 1024);		
		tmp = buf;
		for (i = 0; i < ((*info_header.biwidth) * (*info_header.biheight)); i++) {
			pixel_8[i] = tmp[i%1024];

			if (i % 1024 == 0 && (i != 0)) {
				read_all(fd, buf, 1024);		
				tmp = buf;
			}
		}

		num_pixels = i;
	}
	

	/*Reading in Pixel data for 24 bit*/
	if (*info_header.bibitcount == 24) {
		read_all(fd, buf, 1024);		
		tmp = buf;
		for (i = 0; i < (3 * (*info_header.biwidth) * (*info_header.biheight)); i++) {
			if (i%3 == 0) {
				rgb[i - offset].blue = tmp[i % 1024] & 255;
				offset++;
			} else if (i%3 == 1) {
				rgb[i - offset].green = tmp[i % 1024] & 255;
				offset++;
			} else if (i%3 == 2) {
				rgb[i - offset].red = tmp[i % 1024] & 255;
				array_count++;
			}
			k++;
			if (k == 3) {
				k = 0;
			}
			if (i % 1024 == 0 && (i != 0)) {
				read_all(fd, buf, 1024);		
				tmp = buf;
			}
		}
	}
	

	/*Flag processing*/
	if (iflag == 1) {
		print_header(file_header, info_header);
	}

	if (dflag == 1) {
		print_header(file_header, info_header);
		if (*info_header.bibitcount == 8) {
			printf("Color Table\n");
			printf("index    red    green    blue   alpha\n");
			printf("-------------------------------------\n");
			for (j = 0; j < 256; j++) {
				printf("%d    %d    %d    %d    %d\n",j ,rgb_8[j].red ,rgb_8[j].green ,rgb_8[j].blue, rgb_8[j].alpha );
			}
		} else {
			printf("No Color table\n");
		}
		if (*info_header.bibitcount == 24) {
			printf("Pixel Data\n");
			printf("(row, col) red   green   blue\n");
			printf("-----------------------------");
			for (j = 0; j < array_count; j++) {
				printf("(%d, %d) %u   %u   %u\n",(j%*info_header.biwidth), col, rgb[j].red, rgb[j].green, rgb[j].blue);
				if (j%*info_header.biwidth == 0 && j != 0) {
					col++;
				}
			}
		}
	}

	

	/*writing 8 bit out to file*/
	if (*info_header.bibitcount == 8) {
		int out = open(output_file, O_CREAT|O_WRONLY|O_TRUNC, 0644);
		write_header(file_header, info_header, out);
		if (Rflag == 0) {
			for (j = 0; j < array_count_8; j++) {		

				write(out, &rgb_8[j].blue, 1);
				write(out, &rgb_8[j].green, 1);
				write(out, &rgb_8[j].red, 1);
				write(out, &rgb_8[j].alpha, 1);
			}
		} else {
			for (j = array_count_8 - 1; j >= 0; j--) {		

				write(out, &rgb_8[j].blue, 1);
				write(out, &rgb_8[j].green, 1);
				write(out, &rgb_8[j].red, 1);
				write(out, &rgb_8[j].alpha, 1);
			}
		}

		for (j = 0; j < num_pixels; j++) {
				write(out, &pixel_8[j], 1);
			}
	}


	/*Writing 24 bit out to file*/
	if (*info_header.bibitcount == 24) {
		int out = open(output_file, O_CREAT|O_WRONLY|O_TRUNC, 0644);
		write_header(file_header, info_header, out);
		for (j = 0; j < array_count; j++) {		

			if (bflag == 0) {
				write(out, &rgb[j].blue, 1);
			} else {
				write(out, &blue_val, 1);
			} 
			if (gflag == 0) {
				write(out, &rgb[j].green, 1);
			} else {
				write(out, &green_val, 1);
			} 
			if (rflag == 0) {
				write(out, &rgb[j].red, 1);
			} else {
				write(out, &red_val, 1);
			} 
		}
	}


	/*freeing headers*/
	free(buf);
	free(file_header.bftype);
	free(file_header.bfsize);
	free(file_header.bfres1);
	free(file_header.bfres2);
	free(file_header.offset);
	free(info_header.bisize);
	free(info_header.biwidth);
	free(info_header.biheight);
	free(info_header.biplanes);
	free(info_header.bibitcount);
	free(info_header.bicompression);
	free(info_header.bisizeimage);
	free(info_header.biXpermeter);
	free(info_header.biYpermeter);
	free(info_header.biclrused);
	free(info_header.biclrimportant);

	return 0;
}



ssize_t read_all(int fd, void * const buf, size_t n)
{
        size_t nleft = n;
        ssize_t nread;
        char *p = buf;

        while (nleft > 0 ) {
                if ((nread = read(fd, p, nleft)) == -1) {
                        if (errno == EINTR)
                                nread = 0;
                        else
                                return -1;
                } else if (nread == 0) //EOF
                        break; 
                
                nleft -= nread;
                p += nread;
        }

        return n - nleft; //this should be zero
}

/**
 * Prints information about file and info headers
 * @param The file header structure
 * @param The infor header structure
*/
void print_header(struct bmp_file_header file_header, struct bmp_info_header info_header) {


	printf("Header: %d\n", *file_header.bftype);
	printf("Size of bitmap (bytes): %d\n", *file_header.bfsize);
	//printf("%d\n", *file_header.bfres1);
	//printf("%d\n", *file_header.bfres2);
	printf("offset: %d\n\n", *file_header.offset);

	printf("size of dib: %d\n", *info_header.bisize);
	printf("bitmap width in pixels: %d\n", *info_header.biwidth);
	printf("bitmap height in pixels: %d\n", *info_header.biheight);
	printf("number of color planes: %d\n", *info_header.biplanes);
	printf("number of bits per pixel: %d\n", *info_header.bibitcount);
	printf("compression method: %d\n", *info_header.bicompression);
	printf("image size: %d\n", *info_header.bisizeimage);
	printf("horizontal resolution (pixels per meter): %d\n", *info_header.biXpermeter);
	printf("vertical resolution (pixels per meter): %d\n", *info_header.biYpermeter);
	printf("number of colors: %d\n", *info_header.biclrused);
	printf("number of improtant colors: %d\n\n", *info_header.biclrimportant);	


}


/**
 * writes file and infor headers to a file
 * @param The file header structure
 * @param The infor header structure
*/
void write_header(struct bmp_file_header file_header, struct bmp_info_header info_header, int out) {

	write(out, file_header.bftype, 2);
	write(out, file_header.bfsize, 4);
	write(out, file_header.bfres1, 2);
	write(out, file_header.bfres2, 2);
	write(out, file_header.offset, 4);

	write(out, info_header.bisize, 4);
	write(out, info_header.biwidth, 4);
	write(out, info_header.biheight, 4);
	write(out, info_header.biplanes, 2);
	write(out, info_header.bibitcount, 2);
	write(out, info_header.bicompression, 4);
	write(out, info_header.bisizeimage, 4);
	write(out, info_header.biXpermeter, 4);
	write(out, info_header.biYpermeter, 4);
	write(out, info_header.biclrused, 4);
	write(out, info_header.biclrimportant, 4);

}








